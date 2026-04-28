package com.bookmarks.TypeAhead.search;

public class TreeNode {
    char data;
    TreeNode[] children = new TreeNode[26];
    int freq;

    public TreeNode(char data) {
        this.data = data;
    }
}
