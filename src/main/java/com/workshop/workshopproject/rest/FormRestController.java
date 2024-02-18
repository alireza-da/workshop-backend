package com.workshop.workshopproject.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.enums.FormType;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.FormServiceImpl;
import com.workshop.workshopproject.service.OfferedWorkshopServiceImpl;
import com.workshop.workshopproject.user.RoleAuthentication;
import com.workshop.workshopproject.utils.ConvertFromString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;


@RestController
@RequestMapping("/api")
public class FormRestController {
    private FormServiceImpl formServiceImpl;
    private OfferedWorkshopServiceImpl offeredWorkshopServiceImpl;
    private QueryManager queryManager;
    private RoleAuthentication roleAuthentication;
    private ConvertFromString convertFromString;

    @PersistenceContext
    private EntityManager entityManager;


    public FormRestController(FormServiceImpl formServiceImpl,RoleAuthentication roleAuthentication, OfferedWorkshopServiceImpl offeredWorkshopServiceImpl, ConvertFromString convertFromString, QueryManager queryManager) {
        this.formServiceImpl = formServiceImpl;
        this.offeredWorkshopServiceImpl = offeredWorkshopServiceImpl;
        this.queryManager = queryManager;
        this.roleAuthentication = roleAuthentication;
        this.convertFromString = convertFromString;
    }

    @Transactional
    @PostMapping("/saveForm")
    public ResponseEntity save(@RequestHeader Map<String, Object> jsonHeader, @RequestBody(required = false) Map<String, Object> jsonBody) throws RuntimeException, JsonProcessingException {
        System.out.println(jsonHeader);

        User user = queryManager.getUser((String) jsonHeader.get("authorization"));
        Role role;
        int workshopId = (int) jsonBody.get("workshop_id");
        OfferedWorkshop offeredWorkshop = offeredWorkshopServiceImpl.findById(workshopId);
        if ((role = user.hasRole(Supervisor.class)) == null) {
            System.out.println("jfsdgbfsdg");
            return new ResponseEntity<>("NOT ALLOWED", HttpStatus.BAD_REQUEST);
        }

//        if (offeredWorkshop.get)
        Form form = new Form();
//        if(formAbout == FormAbout.Grader) {
//            if (!roleAuthentication.authAdmin(jsonHeader)){
//                System.out.println(formAbout);
//                return null;
//            }
//        } else if (formAbout == FormAbout.Student) {
//            if (!roleAuthentication.authSupervisor(jsonHeader)){
//                return null;
//            }
//        }

        String title = (String) jsonBody.get("title");
        FormType formType = FormType.valueOf((String) jsonBody.get("formType"));
        WorkshopFormRelation workshopFormRelation = new WorkshopFormRelation();
        workshopFormRelation.setFormType(formType);

        workshopFormRelation.setOfferedWorkshop(offeredWorkshop);
//        String createdDate1 = (String) jsonBody.get("createdDate");
        String deadline1 = (String) jsonBody.get("deadline");
        Calendar deadline = convertFromString.toCalendar(convertFromString.convertTime(deadline1));
        System.out.println(offeredWorkshop);


        Date date= new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        Calendar createdDate = convertFromString.toCalendar(convertFromString.convertTime(timestamp.toString()));

        System.out.println(offeredWorkshop);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Question> questionBody = (List) jsonBody.get("questions");
        form.setTitle(title);
        form.setCreatedDate(createdDate);
        form.setDeadline(deadline);
        form.getWorkshopFormRelations().add(workshopFormRelation);

        for (int index = 0; index < questionBody.size(); ++index) {
            String questionPojo = objectMapper.writeValueAsString(questionBody.get(index));
            Question question = objectMapper.readValue(questionPojo, Question.class);
            form.getQuestions().add(question);
            question.setForm(form);
        }
        workshopFormRelation.setForm(form);
        entityManager.persist(form);
        return new ResponseEntity<>(form, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/forms")
    public List<Form> findAll() {
        return formServiceImpl.findAll();
    }

    // add mapping for GET /employees/{employeeId}

    @RequestMapping(value = "/forms/{formId}", method = RequestMethod.GET)
    @GetMapping("/forms/{formId}")
    public Form getContactPoint(@PathVariable int formId) throws RuntimeException {
        try {
            Form form = formServiceImpl.findById(formId);

            if (form == null) {
                throw new CustomException("form id not found - " + form);
            }

            return form;
        } catch (Exception exc) {
            throw new RuntimeException();
        }


    }

    // add mapping for POST /employees - add new employee

    @PostMapping("/forms")
    public Form addContactPoint(@RequestBody(required = false) Form form) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        form.setId(0);

        formServiceImpl.save(form);

        return form;
    }

    @DeleteMapping("/forms")
    public void delete(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader){
//        if(!(roleAuthentication.authSupervisor(jsonHeader))){
//            return;
//        }

        formServiceImpl.deleteById((int) jsonBody.get("formId"));

    }

    @PutMapping("/forms")
    public void update(@RequestHeader Map<String, Object> jsonHeader,@ModelAttribute ("form") Form graderEvaluationForm){
//        if(!(roleAuthentication.authSupervisor(jsonHeader))){
//            return;
//        }
        entityManager.merge(graderEvaluationForm);

    }

}
