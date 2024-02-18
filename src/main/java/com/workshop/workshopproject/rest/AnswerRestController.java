package com.workshop.workshopproject.rest;


import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.AnswerService;
import com.workshop.workshopproject.service.FilledEvaluationFormService;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/")
public class AnswerRestController {

    private EntityManager entityManager;
    private AnswerService answerService;
    private QueryManager queryManager;
    private FilledEvaluationFormService filledEvaluationFormService;

    public AnswerRestController(EntityManager entityManager, AnswerService answerService, QueryManager queryManager) {
        this.entityManager = entityManager;
        this.answerService = answerService;
        this.queryManager = queryManager;
    }

    @Transactional
    @GetMapping("saveAnswer")
    public Answer save(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader){
        if (!(authGrader(jsonHeader))){
            return null;
        }
        String data = (String) jsonBody.get("data");
        int filledId = (int) jsonBody.get("filled_id");
        FilledEvaluationForm filledEvaluationForm = filledEvaluationFormService.findById(filledId);
        Answer answer = new Answer();
        answer.setData(data);
        answer.setFilledEvaluationForm(filledEvaluationForm);
        entityManager.persist(answer);
        return answer;
    }

    @GetMapping("/answers")
    public List<Answer> getAnswers() {
        return answerService.findAll();
    }

    @RequestMapping(value = "/answers/{answerId}", method = RequestMethod.GET)
    @GetMapping("/answers/{answerId}")
    public Answer getContactPoint(@PathVariable int answerId) throws RuntimeException {
        try {
            Answer contactPoint = answerService.findById(answerId);

            if (contactPoint == null) {
                throw new CustomException("Contact Point id not found - " + answerId);
            }

            return contactPoint;
        } catch (Exception exc) {
            throw new RuntimeException();
        }


    }

    @DeleteMapping("deleteAnswer")
    public void delete(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader){
        if (!(authGrader(jsonHeader))){
            return;
        }
        answerService.deleteById((int) jsonBody.get("answer_id"));
    }

    @Transactional
    @PutMapping("updateAnswer")
    public void update(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader){
        if (!(authGrader(jsonHeader))){
            return;
        }

        String data = (String) jsonBody.get("data");
        int filledId = (int) jsonBody.get("filled_id");
        FilledEvaluationForm filledEvaluationForm = filledEvaluationFormService.findById(filledId);
        Answer answer = answerService.findById((int) jsonBody.get("answer_id"));
        answer.setData(data);
        answer.setFilledEvaluationForm(filledEvaluationForm);
        entityManager.merge(answer);

    }


    public boolean authGrader(@RequestHeader Map<String, Object> jsonHeader){
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        Role grader;
        if ((grader = user.hasRole(Grader.class)) == null) {
            throw new RuntimeException(user.getRoles().toString() + " ,your roles don't have accessibility to this operation");
        }
        return true;
    }
}
