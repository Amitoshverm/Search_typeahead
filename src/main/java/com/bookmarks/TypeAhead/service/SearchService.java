package com.bookmarks.TypeAhead.service;

import com.bookmarks.TypeAhead.entity.SearchTerm;
import com.bookmarks.TypeAhead.repository.SearchTermRepository;
import com.bookmarks.TypeAhead.search.Trie;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class SearchService implements ApplicationListener<ApplicationReadyEvent> {

    private final Trie trie;
    private final SearchTermRepository searchTermRepository;
    private final RedisTemplate<String, Object> redisTemplate;


    public SearchService(Trie trie,
                         SearchTermRepository searchTermRepository,
                         RedisTemplate<String, Object> redisTemplate) {
        this.trie = trie;
        this.searchTermRepository = searchTermRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        loadTrieFromDatabase();
    }

    public void loadTrieFromDatabase() {
        List<SearchTerm> allTerms = searchTermRepository.findAll();
        System.out.println("Terms from DB: " + allTerms.size());
        for (SearchTerm term : allTerms) {
            System.out.println("Inserting: " + term.getWord() + " freq: " + term.getFrequency());
            trie.insertWithFrequency(term.getWord(), term.getFrequency());
        }
        System.out.println("Trie loaded with " + allTerms.size() + " terms");
    }


    public List<String> getSuggestions(String prefix) {
        if (prefix == null || prefix.length() < 2) {
            return new ArrayList<>();
        }

        String lowerPrefix = prefix.toLowerCase();
        String cacheKey = "suggest:" + lowerPrefix;

        // 1. check Redis
        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            System.out.println("Cache hit for: " + lowerPrefix);
            return (List<String>) cached;
        }

        // 2. ask Trie
        System.out.println("Cache miss for: " + lowerPrefix);
        List<String> suggestions = trie.getSuggestions(lowerPrefix);

        // 3. save to Redis
        redisTemplate.opsForValue().set(cacheKey, suggestions, 300, TimeUnit.SECONDS);

        return suggestions;
    }
    public void searchWord(String word) {
        String lowerWord = word.toLowerCase();

        // 1. check if word exists in DB
        Optional<SearchTerm> existing = searchTermRepository.findByWord(lowerWord);

        if (existing.isPresent()) {
            // increment frequency
            SearchTerm term = existing.get();
            term.setFrequency(term.getFrequency() + 1);
            searchTermRepository.save(term);
        } else {
            // add new word
            SearchTerm newTerm = new SearchTerm();
            newTerm.setWord(lowerWord);
            newTerm.setFrequency(1);
            searchTermRepository.save(newTerm);
            trie.insert(lowerWord); // add to Trie
        }

        // 2. update Trie frequency
        trie.incrementFrequency(lowerWord);

        // 3. invalidate Redis cache for all prefixes of this word
        for (int i = 2; i <= lowerWord.length(); i++) {
            String prefix = lowerWord.substring(0, i);
            redisTemplate.delete("suggest:" + prefix);
        }
    }
}
