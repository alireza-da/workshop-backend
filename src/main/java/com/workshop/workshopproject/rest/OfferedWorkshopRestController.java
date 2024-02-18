package com.workshop.workshopproject.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.workshopproject.config.JwtTokenUtil;
import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.enums.RequestStatus;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.AccountantServiceImpl;
import com.workshop.workshopproject.service.OfferedWorkshopServiceImpl;
import com.workshop.workshopproject.service.RoleServiceImpl;
import com.workshop.workshopproject.service.UserServiceImpl;
import com.workshop.workshopproject.user.RoleAuthentication;
import com.workshop.workshopproject.utils.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class OfferedWorkshopRestController {
    private OfferedWorkshopServiceImpl offeredWorkshopServiceImpl;
    private UserServiceImpl userServiceImpl;
    private RoleServiceImpl roleServiceImpl;
    private AccountantServiceImpl accountantService;
    private DateHandler dateHandler;
    @PersistenceContext
    private EntityManager entityManager;

    private QueryManager queryManager;
    private RoleAuthentication roleAuthentication;

    @Autowired
    public OfferedWorkshopRestController(OfferedWorkshopServiceImpl offeredWorkshopServiceImpl, UserServiceImpl userServiceImpl, QueryManager queryManager, RoleServiceImpl roleServiceImpl, AccountantServiceImpl accountantService, RoleAuthentication roleAuthentication, DateHandler dateHandler) {
        this.offeredWorkshopServiceImpl = offeredWorkshopServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.queryManager = queryManager;
        this.roleServiceImpl = roleServiceImpl;
        this.accountantService = accountantService;
        this.roleAuthentication = roleAuthentication;
        this.dateHandler = dateHandler;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/offered-workshops")
    public List<OfferedWorkshop> findAll() {
        return offeredWorkshopServiceImpl.findAll();
    }

    @RequestMapping(value = "/offered-workshops/{offeredWorkshopId}", method = RequestMethod.GET)
    @GetMapping("/offered-workshops/{offeredWorkshopId}")
    public OfferedWorkshop getWorkshop(@PathVariable int offeredWorkshopId) throws RuntimeException {
        try {
            OfferedWorkshop offeredWorkshop = offeredWorkshopServiceImpl.findById(offeredWorkshopId);

            if (offeredWorkshop == null) {
                throw new CustomException("Offered Workshop id not found - " + offeredWorkshopId);
            }
            return offeredWorkshop;
        } catch (Exception exc) {
            throw new RuntimeException();
        }
    }

    @Transactional
    @PostMapping(value = "/offered-workshops")
    public ResponseEntity addOfferedWorkshop(@RequestHeader Map<String, Object> jsonHeader, @RequestBody(required = false) Map<String, Object> json) throws NoSuchAlgorithmException, JsonProcessingException {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        System.out.println(jsonHeader);

        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);

        if ((user.hasRole(Manager.class)) == null) {
            return new ResponseEntity<>("NOT ALLOWED", HttpStatus.BAD_REQUEST);
        }

        System.out.println("here");
        int workshop_id = (int) json.get("workshop_id");
        Workshop workshop = (Workshop) entityManager.createQuery("SELECT workshop from Workshop workshop where workshop.id = :workshop_id")
                .setParameter("workshop_id", workshop_id)
                .getResultList()
                .get(0);
        int roleId = (int) json.get("role_id");
        Role role = roleServiceImpl.findById(roleId);
        if (role.getClass() != Supervisor.class) {
            return null;
        }
//        offeredWorkshop.setId(0);
        json.remove("workshop_id");
        json.remove("role_id");
        ObjectMapper objectMapper = new ObjectMapper();
        String pojoBody = objectMapper.writeValueAsString(json);
        OfferedWorkshop offeredWorkshop = objectMapper.readValue(pojoBody, OfferedWorkshop.class);
        offeredWorkshop.setWorkshop(workshop);
        offeredWorkshop.setSupervisor((Supervisor) role);
        entityManager.persist(offeredWorkshop);
        return new ResponseEntity<>(offeredWorkshop, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/offered-workshops/register")
    public ResponseEntity register(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader) throws JsonProcessingException {
        String token = (String)jsonHeader.get("authorization");
        // System.out.println(token);
        // System.out.println(username);
        int workshopId = (int)jsonBody.get("workshop_id");
        List<Accountant> accountants = accountantService.findAll();
        if (accountants == null || accountants.size() == 0) {
            return new ResponseEntity<>("Sorry, We are not able to handle your request at the moment!", HttpStatus.BAD_REQUEST);
        }
        Accountant accountant = accountants.get(0);
        User user = queryManager.getUser(token);
        OfferedWorkshop offeredWorkshop = (OfferedWorkshop) entityManager.createQuery("SELECT workshop from OfferedWorkshop workshop where workshop.id = :workshopId")
                .setParameter("workshopId", workshopId)
                .getResultList()
                .get(0);
        Role student;
        if ((student = user.hasRole(Student.class)) == null) {
            student = new Student();
            user.getRoles().add(student);
            entityManager.persist(student);
        }
        if (offeredWorkshop.hasEnrolled((Student)student)) {
            return new ResponseEntity<>("You Have Already Enrolled in this Workshop!", HttpStatus.BAD_REQUEST);
        }
        List<OfferedWorkshop> offeredWorkshops = offeredWorkshop.hasCoincidence(offeredWorkshop, ((Student)student));
        if (offeredWorkshops.size() != 0) {
            return new ResponseEntity<>(offeredWorkshops, HttpStatus.BAD_REQUEST);
        }

        Registration registration = new Registration();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Payment> paymentBody = (List)jsonBody.get("payments");
        ArrayList<Payment> payments = new ArrayList<>();
        BigDecimal totalAmount = new BigDecimal(0.0);

        for (int index = 0; index < paymentBody.size(); ++index) {
            String paymentPojo = objectMapper.writeValueAsString(paymentBody.get(index));
            Payment payment = objectMapper.readValue(paymentPojo, Payment.class);
            totalAmount = totalAmount.add(BigDecimal.valueOf(payment.getAmount().doubleValue()));
            payment.setRegistration(registration);
            payment.setAccountant(accountant);
            registration.getPayments().add(payment);
        }

        if (totalAmount.compareTo(offeredWorkshop.getPrice()) < 0) {
            System.out.println(totalAmount.toString());
            return new ResponseEntity<>("Payments Do not Match the Total Price of Workshop!", HttpStatus.BAD_REQUEST);
        } else if (totalAmount.compareTo(offeredWorkshop.getPrice()) > 0) {
            return new ResponseEntity<>("Payments Exceed the Total Price of Workshop!", HttpStatus.BAD_REQUEST);
        }
//        offeredWorkshop.getRequests().add(registration);
        RequestStatusInfo requestStatusInfo = new RequestStatusInfo();
        requestStatusInfo.setRequestStatus(RequestStatus.Pending);
        requestStatusInfo.setRequestedDate(dateHandler.getCurrentDate());
        registration.setRequestStatusInfo(requestStatusInfo);
        registration.setStudent((Student)student);
        registration.setOfferedWorkshop(offeredWorkshop);
        entityManager.persist(registration);


        return new ResponseEntity<>("Registration Successful!", HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/offered-workshops/request")
    public ResponseEntity request(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader) {
        String token = (String)jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        System.out.println(token);
        int workshopId = (int) jsonBody.get("workshop_id");
        OfferedWorkshop offeredWorkshop = (OfferedWorkshop) entityManager.createQuery("SELECT workshop from OfferedWorkshop workshop where workshop.id = :workshopId")
                .setParameter("workshopId", workshopId)
                .getResultList()
                .get(0);
        Role grader;
        if ((grader = user.hasRole(Grader.class)) == null) {
            grader = new Grader();
            user.getRoles().add(grader);
            entityManager.persist(grader);
        }
        if (offeredWorkshop.hasRequested((Grader)grader)) {
            return new ResponseEntity<>("Has already requested", HttpStatus.BAD_REQUEST);
        }
        System.out.println(offeredWorkshop.getDescription());
        GraderRequest graderRequest = new GraderRequest();
        graderRequest.setBody((String)jsonBody.get("body"));
        RequestStatusInfo requestStatusInfo = new RequestStatusInfo();
        requestStatusInfo.setRequestStatus(RequestStatus.Pending);
        requestStatusInfo.setRequestedDate(dateHandler.getCurrentDate());
        graderRequest.setRequestStatusInfo(requestStatusInfo);
        graderRequest.setOfferedWorkshop(offeredWorkshop);
//        offeredWorkshop.getRequests().add(graderRequest);
        graderRequest.setGrader((Grader)grader);
//        ((Grader)grader).getGraderRequests().add(graderRequest);
        entityManager.persist(graderRequest);
        return new ResponseEntity<>("Request Successfully Sent! Your Request Will be Responded via Email!", HttpStatus.OK);
    }

//    @Transactional
//    @PostMapping("offered-workshops/student-evaluation") // ALTER ADDING ROLE
//    public ResponseEntity defineStudentEvaluationForm(@RequestBody Map<String, Object> jsonBody) throws JsonProcessingException {
//        String username = (String) jsonBody.get("username");
//        int workshopId = (int) jsonBody.get("workshop_id");
//        OfferedWorkshop offeredWorkshop = (OfferedWorkshop) entityManager.createQuery("SELECT workshop from OfferedWorkshop workshop where workshop.id = :workshopId")
//                .setParameter("workshopId", workshopId)
//                .getResultList()
//                .get(0);
//        User user = (User)entityManager.createQuery("SELECT user from User user where user.username = :username")
//                .setParameter("username", username)
//                .getResultList()
//                .get(0);
//        Role manager;
//        if ((manager = user.hasRole(Manager.class)) == null) {
//            manager = new Manager();
//            user.getRoles().add(manager);
//            entityManager.persist(manager);
//        }
//
//        Map<String, Object> formRelationJson = new HashMap<>();
//        List<Question> questions = (List)jsonBody.get("questions");
//        System.out.println(questions);
//        ObjectMapper objectMapper = new ObjectMapper();
//        formRelationJson.put("formType", jsonBody.get("formType"));
//        String formRelationString = objectMapper.writeValueAsString(formRelationJson);
//        WorkshopFormRelation workshopFormRelation = objectMapper.readValue(formRelationString, WorkshopFormRelation.class);
//        entityManager.persist(workshopFormRelation);
//        StudentEvaluationForm studentEvaluationForm = new StudentEvaluationForm();
//        studentEvaluationForm.getWorkshopFormRelations().add(workshopFormRelation);
//        if (questions != null) {
//            for (int index = 0; index < questions.size(); ++index) {
//                String questionBody = objectMapper.writeValueAsString(questions.get(index));
//                Question questionPojo = objectMapper.readValue(questionBody, Question.class);
//                entityManager.persist(questionPojo);
//                studentEvaluationForm.getQuestions().add(questionPojo);
//            }
//        }
//        entityManager.persist(studentEvaluationForm);
//        offeredWorkshop.getWorkshopFormRelations().add(workshopFormRelation);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @Transactional
//    @PostMapping("offered-workshops/grader-evaluation")
//    public ResponseEntity defineGraderEvaluationForm(@RequestBody Map<String, Object> jsonBody) throws JsonProcessingException {
//        String username = (String) jsonBody.get("username");
//        int workshopId = (int) jsonBody.get("workshop_id");
//        OfferedWorkshop offeredWorkshop = offeredWorkshopServiceImpl.findById(workshopId);
//        User user = userServiceImpl.findByUsername(username);
//        System.out.println(user.getUsername());
//        Role manager;
//        if ((manager = user.hasRole(Manager.class)) == null) {
//            manager = new Manager();
//            user.getRoles().add(manager);
//            entityManager.persist(manager);
//        }
//
//        Map<String, Object> formRelationJson = new HashMap<>();
//        List<Question> questions = (List)jsonBody.get("questions");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        formRelationJson.put("formType", jsonBody.get("formType"));
//        String formRelationString = objectMapper.writeValueAsString(formRelationJson);
//        WorkshopFormRelation workshopFormRelation = objectMapper.readValue(formRelationString, WorkshopFormRelation.class);
//        entityManager.persist(workshopFormRelation);
//
//        GraderEvaluationForm graderEvaluationForm = new GraderEvaluationForm();
//        graderEvaluationForm.getWorkshopFormRelations().add(workshopFormRelation);
//        if (questions != null) {
//            for (int index = 0; index < questions.size(); ++index) {
//                String questionBody = objectMapper.writeValueAsString(questions.get(index));
//                Question questionPojo = objectMapper.readValue(questionBody, Question.class);
//                entityManager.persist(questionPojo);
//                graderEvaluationForm.getQuestions().add(questionPojo);
//            }
//        }
//        entityManager.persist(graderEvaluationForm);
//        offeredWorkshop.getWorkshopFormRelations().add(workshopFormRelation);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @PostMapping("/offered-workshops/filled-student")
//    public String applyFilledForm(@RequestBody Map<String, Object> jsonBody) {
//        String username = (String) jsonBody.get("username"); // username of the user sending the form
//        int formId = (int)jsonBody.get("form_id");
//        StudentEvaluationForm studentEvaluationForm = (StudentEvaluationForm) entityManager.createQuery("SELECT studentEvaluationForm from StudentEvaluationForm studentEvaluationForm where studentEvaluationForm.id = :formId")
//                .setParameter("formId", formId)
//                .getResultList()
//                .get(0);
//        User user = userServiceImpl.findByUsername(username);
//        Role grader;
//        if ((grader = user.hasRole(Grader.class)) == null) {
//            grader = new Grader();
//            user.getRoles().add(grader);
//            entityManager.persist(grader);
//        }
//
//
//    }

    @PutMapping("/offered-workshops")
    public OfferedWorkshop updateOfferedWorkshop(@RequestBody OfferedWorkshop offeredWorkshop) {

        offeredWorkshopServiceImpl.save(offeredWorkshop);

        return offeredWorkshop;
    }

    @Transactional
    @PatchMapping("/offered-workshops/{offeredWorkshopId}")
    public OfferedWorkshop modifyOfferedWorkshop(@RequestBody(required = false) OfferedWorkshop offeredWorkshop, @PathVariable int offeredWorkshopId) {
        System.out.println(offeredWorkshop.getPrice());
        offeredWorkshopServiceImpl.save(offeredWorkshop);
//        entityManager.merge(offeredWorkshop);
        return offeredWorkshop;
    }

    @DeleteMapping("/offered-workshops/{offeredWorkshopId}")
    public String deleteOfferedWorkshop(@PathVariable int offeredWorkshopId) {

        OfferedWorkshop workshop = offeredWorkshopServiceImpl.findById(offeredWorkshopId);

        // throw exception if null

        if (workshop == null) {
            throw new RuntimeException("Offered Workshop id not found - " + offeredWorkshopId);
        }

        offeredWorkshopServiceImpl.deleteById(offeredWorkshopId);

        return "Deleted offered workshop id - " + offeredWorkshopId;
    }
}
