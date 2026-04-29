# Introduction
## this is my project where i will be using all my backend learning to create a project that will be useful for me and others. I will be using node js and express js for the backend and mongodb for the database. I will be creating a REST API that will allow users to create, read, update and delete data from the database.
## Database which i will be using is Mysql 

# This is for my understanding
## 1. Yes! The Trie lives in RAM because it's a Spring @Component.
## When Spring boots up:
    @Component
    public class Trie {
    TrieNode root = new TrieNode(' ');
    ...
    }
## Spring creates one instance of Trie and keeps it alive in memory (RAM) for the entire lifetime of the app. This is called a Singleton bean — Spring's default.