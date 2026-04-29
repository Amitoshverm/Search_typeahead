package com.bookmarks.TypeAhead.search;

public class TrieNode {
    char data;
    TrieNode[] children = new TrieNode[26];
    int freq;

    public TrieNode(char data) {
        this.data = data;
    }
}
