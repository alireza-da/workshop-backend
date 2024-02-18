package com.workshop.workshopproject.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.GraderRequestServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/")
public class GraderRequestRestController {
    private GraderRequestServiceImpl graderRequestService;
    private QueryManager queryManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public GraderRequestRestController(GraderRequestServiceImpl graderRequestService, QueryManager queryManager) {
        this.graderRequestService = graderRequestService;
        this.queryManager = queryManager;
    }

    // expose "/employees" and return list of employees
    @CrossOrigin(origins = "*")
    @GetMapping("/grader-requests")
    public List<GraderRequest> findAll() {
        return graderRequestService.findAll();
    }

    // add mapping for GET /employees/{employeeId}

    @RequestMapping(value = "/grader-requests/{graderRequestId}", method = RequestMethod.GET)
    @GetMapping("/grader-requests/{graderRequestId")
    public GraderRequest getContactPoint(@PathVariable int graderRequestId) throws RuntimeException {
        try {
            GraderRequest contactPoint = graderRequestService.findById(graderRequestId);

            if (contactPoint == null) {
                throw new CustomException("Contact Point id not found - " + graderRequestId);
            }

            return contactPoint;
        } catch (Exception exc) {
            throw new RuntimeException();
        }
    }

//    @Transactional
    @PostMapping("/grader-requests/status")
    public ResponseEntity setRequestStatus(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader) throws JsonProcessingException, Exception {
        try {
            System.out.println("sadsd");
            String token = (String) jsonHeader.get("authorization");
            System.out.println(token);
            User user = queryManager.getUser(token);
            int requestId = (int)jsonBody.get("request_id");
            System.out.println(requestId);
//            GraderRequest graderRequest = (GraderRequest) entityManager.createQuery("SELECT graderRequest from GraderRequest graderRequest where graderRequest.id = :requestId")
//                    .setParameter("requestId", requestId)
//                    .getResultList()
//                    .get(0);
            GraderRequest graderRequest = graderRequestService.findById(requestId);
//            System.out.println(graderRequest);
//            Role supervisor;
//
//            if ((supervisor = user.hasRole(Supervisor.class)) == null) {
//                supervisor = new Supervisor();
//                user.getRoles().add(supervisor);
////                return new ResponseEntity<>("User Not Allowed", HttpStatus.METHOD_NOT_ALLOWED);
//                graderRequest.getOfferedWorkshop().setSupervisor((Supervisor) supervisor);
//                entityManager.persist(supervisor);
//            }
            System.out.println(graderRequest);
            System.out.println(user.getUsername());
            ObjectMapper objectMapper = new ObjectMapper();
            String requestStatusInfoBody = objectMapper.writeValueAsString(jsonBody.get("request_info"));
            System.out.println(jsonBody.get("request_info"));
            RequestStatusInfo requestStatusInfo = objectMapper.readValue(requestStatusInfoBody, RequestStatusInfo.class);
            BeanUtils.copyProperties(requestStatusInfo, graderRequest.getRequestStatusInfo(), "id");
            System.out.println(graderRequest.getRequestStatusInfo().getRequestStatus());
//            entityManager.merge(graderRequest);
            graderRequestService.save(graderRequest);
            return new ResponseEntity<>("Successful", HttpStatus.OK );
        } catch (Exception exc) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }

    }

    // add mapping for POST /employees - add new employee

    @PostMapping("/grader-requests")
    public GraderRequest addContactPoint(@RequestBody(required = false) GraderRequest contactPoint) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        contactPoint.setId(0);

        graderRequestService.save(contactPoint);

        return contactPoint;
    }

    // add mapping for PUT /employees - update existing employee

    @PutMapping("/grader-requests")
    public GraderRequest updateContactPoint(@RequestBody GraderRequest contactPoint) {

        graderRequestService.save(contactPoint);

        return contactPoint;
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee

    @DeleteMapping("/grader-requests/{graderRequestId}")
    public String deleteContactPoint(@PathVariable int graderRequestId) {

        GraderRequest contactPoint = graderRequestService.findById(graderRequestId);

        // throw exception if null

        if (contactPoint == null) {
            throw new RuntimeException("Contact Point id not found - " + graderRequestId);
        }

        graderRequestService.deleteById(graderRequestId);

        return "Deleted Contact Point id - " + graderRequestId;
    }
}
