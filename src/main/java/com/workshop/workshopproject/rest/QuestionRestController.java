package com.workshop.workshopproject.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class QuestionRestController {
    private EntityManager entityManager;
    private QueryManager queryManager;
    private QuestionService questionService;
    private Object Question;

    @Autowired
    public QuestionRestController(EntityManager entityManager, QueryManager queryManager, QuestionService questionService) {
        this.entityManager = entityManager;
        this.queryManager = queryManager;
        this.questionService = questionService;
    }

    @Transactional
    @PostMapping("/questions")
    public List<Question> save(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader){
        ObjectMapper objectMapper = new ObjectMapper();
        List<Question> questions = new ArrayList<>();
        if(!(authSupervisor(jsonHeader))){
            return questions;
        }
        for (Question q :questions) {
            entityManager.persist(q);
        }
        return questions;
    }



    @DeleteMapping("/questions")
    public void delete(@RequestBody(required = false) Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader){
        if(!(authSupervisor(jsonHeader))){
            return;
        }

        int id = (int) jsonBody.get("question_id");
        questionService.deleteById(id);
    }

    @PutMapping("/questions")
    public void update(@ModelAttribute ("question") Question question, @RequestHeader Map<String, Object> jsonHeader){
        if(!(authSupervisor(jsonHeader))){
            return;
        }
        entityManager.merge(question);
    }


    private boolean authSupervisor(Map<String, Object> jsonHeader) {
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        Role supervisor;
        if ((supervisor = user.hasRole(Supervisor.class)) == null) {
            throw new RuntimeException(user.getRoles().toString() + " ,your roles don't have accessibility to this operation");
        }
        return true;
    }
}
