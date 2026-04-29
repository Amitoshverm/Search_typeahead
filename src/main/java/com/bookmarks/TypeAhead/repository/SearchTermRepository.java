package com.bookmarks.TypeAhead.repository;

import com.bookmarks.TypeAhead.entity.SearchTerm;
import com.bookmarks.TypeAhead.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SearchTermRepository extends JpaRepository<SearchTerm, Long> {

    Optional<SearchTerm> findByWordAndUser(String word, Users user);
    List<SearchTerm> findTop5ByUserOrderByFrequencyDesc(Users user);
    Optional<SearchTerm> findByWord(String word);
}
