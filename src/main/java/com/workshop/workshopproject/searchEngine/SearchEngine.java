package com.workshop.workshopproject.searchEngine;

import com.workshop.workshopproject.dao.OfferedWorkshopRepository;
import com.workshop.workshopproject.entity.OfferedWorkshop;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


@Configuration
public class SearchEngine {
    private List<URL> urls;
    private List<String> logicalWords;
    private EntityManager entityManager;


    public SearchEngine(){

    }


    public SearchEngine(List<URL> urls, List<String> logicalWords,EntityManager entityManager) {
        this.urls = urls;
        this.logicalWords = logicalWords;
        this.entityManager = entityManager;

    }

    public List<String> getLogicalWords() {
        return logicalWords;
    }

    public void setLogicalWords(List<String> logicalWords) {
        this.logicalWords = logicalWords;
    }

    public List<URL> getUrls() {
        return urls;
    }

    public void setUrls(List<URL> urls) {
        this.urls = urls;
    }

    public HashMap<Integer,URL> search(String keyword) throws IOException {
        HashMap<Integer,URL> results = new HashMap<>();
        int score = 0;
        List<String> words = new ArrayList<>();
        words = Arrays.asList(keyword.split(" "));
        String domain = "http://localhost:8081/";
        for (URL url:urls) {
            StringBuilder body = new StringBuilder();
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
                body.append(" ");
            }

            for (String word: words) {
                if (body.toString().contains(word)){
                    if (isInteger(word)){
                        score += 1;
                    }
                    else if (logicalWords.contains(word)){
                        score += 10;
                    }
                    else score += 100;
                }
                else if (url.toString().contains(word)){
                    score += 150;
                }

                results.put(score,url);
                score = 0;
            }

            if (body.toString().contains(keyword)){
                results.put(1000,url); // add score 1000 for matching whole keyword
            }

            for (int range = 2; range < words.size(); range++) {// search for matching sequence of words
                for (int i = 0; i < words.size(); i++) {
                    StringBuilder sequence = new StringBuilder();
                    for (int j = i; j < i + range; j++) {
                        sequence.append(words.get(j));
                    }
                    System.out.println(sequence.toString());
                    if (body.toString().contains(sequence.toString())) {
                        score += range*75;
                    }
                }
            }
            results.put(score,url);
        }
        if (results.isEmpty()){
            return null;
        }
        return results;
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public void addURL(URL url){
        urls.add(url);
    }
}
