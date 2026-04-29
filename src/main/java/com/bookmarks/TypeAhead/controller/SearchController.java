package com.bookmarks.TypeAhead.controller;

import com.bookmarks.TypeAhead.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/suggest")
    public ResponseEntity<List<String>> getSuggestions(
            @RequestParam String prefix) {
        List<String> suggestions = searchService.getSuggestions(prefix);
        return new ResponseEntity<>(suggestions, HttpStatus.OK);
    }

    @PostMapping("/query")
    public ResponseEntity<Void> searchWord(@RequestParam String word) {
        searchService.searchWord(word);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
