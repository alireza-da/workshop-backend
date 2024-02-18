package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.OfferedWorkshop;
import com.workshop.workshopproject.searchEngine.SearchEngine;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.IOException;
import java.net.URL;
import java.util.*;


@RestController
@RequestMapping("/api")
public class SearchRestController {

    private SearchEngine searchEngine;
    private OfferedWorkshopRestController offeredWorkshopRestController;

    public SearchRestController(SearchEngine searchEngine, OfferedWorkshopRestController offeredWorkshopRestController) {
        this.searchEngine = searchEngine;
        this.offeredWorkshopRestController = offeredWorkshopRestController;
    }

    @GetMapping("/search")
    public List<String> search(@RequestBody Map<String, Object> jsonBody) throws IOException {

        List<String> result = new ArrayList<>();
        List<URL> allowedUrls = new ArrayList<>();
        HashMap<Integer,String> urls = new HashMap<>();

        String domain = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/offered-workshops").toUriString();
        System.out.println(domain);
        URL url = new URL(domain);
        domain = "http://localhost:3000/workshop"; // ask about workshops url

        allowedUrls.add(url);
        for (OfferedWorkshop o:offeredWorkshopRestController.findAll()) {
            url = new URL(domain+"/"+o.getId());
            allowedUrls.add(url);
        }
        searchEngine.setUrls(allowedUrls);

        List<String> logicalWords = new ArrayList<>();
        logicalWords.add("and");
        logicalWords.add("or");
        logicalWords.add("as");
        logicalWords.add("so");
        logicalWords.add("if");
        logicalWords.add("then");
        logicalWords.add("else");
        logicalWords.add("is");
        logicalWords.add("are");
        searchEngine.setLogicalWords(logicalWords);
        
        String keyword = (String) jsonBody.get("keyword");

        HashMap<Integer,URL> results = searchEngine.search(keyword);

        if (results.isEmpty()){
            throw new RuntimeException("Nothing Found");
        }

        for (Map.Entry<Integer,URL> entry:results.entrySet()) {
            if (entry.getKey() > 100){
                urls.put(entry.getKey(),entry.getValue().toString());
            }
        }
        List<Integer> scores = new ArrayList<>();
        for (Map.Entry<Integer,String> entry:urls.entrySet()) {
            scores.add(entry.getKey());
        }
        Collections.sort(scores);
        for (int score:scores) {
            result.add(urls.get(score));
        }
        return result;
    }
}
