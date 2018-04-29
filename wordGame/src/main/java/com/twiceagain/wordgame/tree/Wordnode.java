/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.wordgame.tree;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Tree shaped data structure for words.
 *
 * @author xavier
 */
public class Wordnode {

    Map<Character, Wordnode> children = new HashMap<>();
    Wordnode parent = null;
    int depth = 0;
    private static final Logger LOG = Logger.getLogger(Wordnode.class.getName());
    
    

    /**
     * Construct empty tree
     */
    public Wordnode() {
    }

    /**
     * Loads tree from file of words.
     *
     * @param fileName
     * @throws java.io.IOException
     */
    @Deprecated
    public void load(String fileName) throws IOException {

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(this::add);
        }

    }
    /**
     * Load dictionnary from resources.
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    public void load() throws  URISyntaxException, IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("all-root-ascii.txt");
        url.getPath();
        System.out.printf("Url resource : %s \npath : %s\n", url, url.getPath());
        try (Stream<String> stream = Files.lines(Paths.get(url.getPath()))) {
            stream
                    .filter(Words::valid)
                    .forEach(this::add);
        }

    }

    public Wordnode add(Character c) {
        // If letter already created ...
        if (children.containsKey(c)) {
            return children.get(c);
        }
        // Otherwise ..
        Wordnode w = new Wordnode();
        w.depth = depth + 1;
        w.parent = this;
        children.put(c, w);
        return w;
    }

    public Wordnode add(String s) {
        if (s == null || s.isEmpty()) {
            return this;
        }
        Wordnode w = this;
        for (Character c : s.toCharArray()) {
            w = w.add(c);
        }
        return w;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");     
        for (Character c : children.keySet()) {
            for (int i = depth; i >= 0; i--) {
                sb.append(" |");
            }
            sb.append("->");
            sb.append(c);
            sb.append(children.get(c).toString());
        }
        return sb.toString();
    }

    /**
     * Find the string. Returning the end of string node, or null if not found.
     *
     * @param s
     * @return
     */
    public Wordnode find(String s) {
        if (s == null || s.isEmpty()) {
            return this;
        }
        Wordnode w = this;
        for (Character c : s.toCharArray()) {
            w = w.children.get(c);
            if (w == null) {
                return null;
            }
        }
        return w;
    }
    
    /**
     * Does the node have children ?
     * @return 
     */
    public boolean hasChildren() {
        return !(children == null || children.isEmpty());
    }
    
    
   
}
