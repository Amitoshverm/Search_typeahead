# Search Typeahead — Backend

A production-grade search typeahead API built with Spring Boot,
featuring in-memory Trie data structure, Redis caching, and JWT auth.

## Architecture
[paste the architecture diagram screenshot here]

## Tech Stack
- Java 17 + Spring Boot 3
- Trie (custom implementation) — in-memory prefix search
- Redis — lazy prefix cache, 5min TTL
- MySQL — persistent storage
- JWT — stateless authentication

## How it works
1. On startup, all words load from MySQL into Trie (RAM)
2. User types prefix → Redis checked first (< 2ms)
3. Cache miss → Trie lookup (< 5ms)
4. Result cached in Redis for next request
5. On search → frequency updated in MySQL + Trie
6. Redis cache invalidated for all prefixes of searched word

## Performance
    | Layer       | Latency  |
    |-------------|----------|
    | Redis hit   | ~2ms     |
    | Trie lookup | ~5ms     |
    | MySQL query | ~150ms   |

## API Endpoints
    POST /user/signup     — register user
    POST /user/login      — returns JWT token
    GET  /search/suggest  — get top 5 suggestions
    POST /search/query    — save search + update frequency

## Run locally
# Prerequisites: Java 17, MySQL, Redis

# 1. Create MySQL database
mysql -u root -p
CREATE DATABASE bookmark_app;

# 2. Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookmark_app
spring.datasource.username=your_username
spring.datasource.password=your_password

# 3. Run
mvn clean spring-boot:run





# For my understandings
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