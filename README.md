# Introduction
## this is my project where i will be using all my backend learning to create a project that will be useful for me and others. I will be using node js and express js for the backend and mongodb for the database. I will be creating a REST API that will allow users to create, read, update and delete data from the database.
## Database which i will be using is Mysql 

# This is for my understanding
## 1. Yes! The Trie lives in RAM because it's a Spring @Component. When Spring boots up:
    @Component
    public class Trie {
    TrieNode root = new TrieNode(' ');
    ...
    }
## Spring creates one instance of Trie and keeps it alive in memory (RAM) for the entire lifetime of the app. This is called a Singleton bean — Spring's default.

## 2.App starts
    → loadTrieFromDB() runs
    → words go from MySQL → Trie (RAM)
    → Redis is still EMPTY ✅ (this is correct)

## User searches "ap" — FIRST TIME
    → check Redis → empty = MISS
    → ask Trie → returns [apple, application, apply...]
    → NOW save to Redis → "suggest:ap" = [apple, application, apply...]
    → return suggestions

## User searches "ap" — SECOND TIME
    → check Redis → found! = HIT
    → return immediately, Trie never touched 


## when the app starts, it loads all the search terms from MySQL into the Trie in RAM. This allows for fast lookups when users search for suggestions. The Trie is a data structure that is optimized for prefix searches, making it ideal for autocomplete functionality.
## loadWordsFromDB() is called at startup to populate the Trie with the search terms from MySQL. This way, when a user searches for a term, the app can quickly traverse the Trie to find all matching suggestions without having to query the database each time.
    MySQL (permanent storage)        RAM (fast, temporary)
    ─────────────────────────        ─────────────────────
    search_term table                Trie object
    ┌──────────────────────┐         ┌──────────────────┐
    │ word      frequency  │  ──→    │      root        │
    │ apple     10         │  load   │     /    \       │
    │ app        5         │  on     │    a      b      │
    │ apply      3         │  start  │   /        \     │
    │ ball       8         │         │  p          a    │
    └──────────────────────┘         └──────────────────┘