package com.workshop.workshopproject.rest;


import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.service.AnswerService;
import com.workshop.workshopproject.service.FilledEvaluationFormService;
import com.workshop.workshopproject.service.GraderWorkshopRelationService;
import com.workshop.workshopproject.service.QuestionServiceImpl;
import com.workshop.workshopproject.user.RoleAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FilledEvaluationFormRestController {

    @PersistenceContext
    private EntityManager entityManager;
    private FilledEvaluationFormService filledEvaluationFormService;
    private GraderWorkshopRelationService graderWorkshopRelationService;
    private AnswerService answerService;
    private FormRestController formRestController;
    private RoleAuthentication roleAuthentication;
    private QuestionServiceImpl questionService;

    @Autowired
    public FilledEvaluationFormRestController(EntityManager entityManager, FilledEvaluationFormService filledEvaluationFormService, GraderWorkshopRelationService graderWorkshopRelationService, AnswerService answerService, FormRestController formRestController, QuestionServiceImpl questionService) {
        this.entityManager = entityManager;
        this.filledEvaluationFormService = filledEvaluationFormService;
        this.graderWorkshopRelationService = graderWorkshopRelationService;
        this.answerService = answerService;
        this.formRestController = formRestController;
        this.questionService = questionService;
    }

    @GetMapping("/getAllFilledEvForms")
    public List<FilledEvaluationForm> findAll(@RequestHeader Map<String, Object> jsonHeader, @RequestBody(required = false) Map<String, Object> jsonBody){
        roleAuthentication.authSupervisor(jsonHeader);
        return filledEvaluationFormService.findAll();
    }

    @GetMapping("/getFilledEvForm")
    public FilledEvaluationForm findById(@RequestHeader Map<String, Object> jsonHeader, @RequestBody(required = false) Map<String, Object> jsonBody){
        roleAuthentication.authSupervisor(jsonHeader);
        int id = (int) jsonBody.get("filledFormId");
        return filledEvaluationFormService.findById(id);
    }

    @Transactional
    @PostMapping("/saveFilledEvForm")
    public FilledEvaluationForm save(@RequestHeader Map<String, Object> jsonHeader, @RequestBody(required = false) Map<String, Object> jsonBody){
        roleAuthentication.authSupervisor(jsonHeader);
        FilledEvaluationForm filledEvaluationForm = new FilledEvaluationForm();


        List<Integer> answersId = (List<Integer>) jsonBody.get("answers_id");
        int graderWorkshopRelationsId = (int) jsonBody.get("graderWorkshopRelations_id");

        filledEvaluationForm.setGraderWorkshopRelation(graderWorkshopRelationService.findById(graderWorkshopRelationsId));

        List<Answer> answers = new ArrayList<>();
        for(int id:answersId){
            answers.add(answerService.findById(id));
        }
        filledEvaluationForm.setAnswers(answers);

        entityManager.persist(filledEvaluationForm);
        return filledEvaluationForm;

    }

    @Transactional
    @PostMapping("/fill_form")
    public ResponseEntity fillForm(@RequestHeader Map<String, Object> jsonHeader,@RequestBody(required = false) Map<String, Object> jsonBody) {
        FilledEvaluationForm filledEvaluationForm = new FilledEvaluationForm();
        int graderRelationId = (int) jsonBody.get("grader_relation_id");
        GraderWorkshopRelation graderWorkshopRelation = (GraderWorkshopRelation) entityManager.createQuery("select graderWorkshopRelation from GraderWorkshopRelation graderWorkshopRelation where graderWorkshopRelation.id = :graderRelationId")
                .setParameter("graderRelationId", graderRelationId)
                .getResultList()
                .get(0);
        int formId = (int) jsonBody.get("form_id");
        Form form = (Form) entityManager.createQuery("select form from Form form where form.id = :formId")
                .setParameter("formId", formId)
                .getResultList()
                .get(0);
        List<Map<String, Object>> answers = (List<Map<String, Object>>) jsonBody.get("answers");
        filledEvaluationForm.setGraderWorkshopRelation(graderWorkshopRelation);
        filledEvaluationForm.setForm(form);
        filledEvaluationForm.setOfferedWorkshop(graderWorkshopRelation.getGraderRequest().getOfferedWorkshop());
        System.out.println("dsfsd");
        for (int index = 0; index < answers.size(); ++index) {
            int questionId = (int) answers.get(index).get("question_id");
            String answer = (String) answers.get(index).get("answer_data");
            Question question = questionService.findById(questionId);
            Answer newAnswer = new Answer();
            newAnswer.setData(answer);
            newAnswer.setQuestion(question);
            newAnswer.setFilledEvaluationForm(filledEvaluationForm);
            entityManager.persist(newAnswer);
        }
        entityManager.persist(filledEvaluationForm);
        return new ResponseEntity<>("Form Fill Successful", HttpStatus.OK);

    }

    @GetMapping("/filled_eval_forms")
    public List<FilledEvaluationForm> getFilledEvals() {
        return filledEvaluationFormService.findAll();
    }

    @RequestMapping(value = "/filled_evals/{eval_id}", method = RequestMethod.GET)
    @GetMapping("/filled_evals/{eval_id}")
    public FilledEvaluationForm getContactPoint(@PathVariable int eval_id) throws RuntimeException {
        try {
            FilledEvaluationForm contactPoint = filledEvaluationFormService.findById(eval_id);

            if (contactPoint == null) {
                throw new CustomException("Contact Point id not found - " + contactPoint.getId());
            }

            return contactPoint;
        } catch (Exception exc) {
            throw new RuntimeException();
        }


    }


    @DeleteMapping("/deleteFilledEvForm")
    public void delete(@RequestHeader Map<String, Object> jsonHeader,@RequestBody(required = false) Map<String, Object> jsonBody){
        roleAuthentication.authSupervisor(jsonHeader);
        int id = (int) jsonBody.get("fille_formId");
        filledEvaluationFormService.deleteById(id);
    }

    @PutMapping("/updateFilledEvForm")
    public FilledEvaluationForm update(@RequestHeader Map<String, Object> jsonHeader, @RequestBody(required = false) Map<String, Object> jsonBody){

        FilledEvaluationForm filledEvaluationForm = filledEvaluationFormService.findById((int) jsonBody.get("filled_formId"));

        List<Integer> answersId = (List<Integer>) jsonBody.get("answers_id");

        int graderWorkshopRelationsId = (int) jsonBody.get("graderWorkshopRelations_id");

        filledEvaluationForm.setGraderWorkshopRelation(graderWorkshopRelationService.findById(graderWorkshopRelationsId));

        List<Answer> answers = new ArrayList<>();
        for(int id:answersId){
            answers.add(answerService.findById(id));
        }
        filledEvaluationForm.setAnswers(answers);

        entityManager.merge(filledEvaluationForm);
        return filledEvaluationForm;
    }
}
