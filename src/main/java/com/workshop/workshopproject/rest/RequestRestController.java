package com.workshop.workshopproject.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.enums.GraderType;
import com.workshop.workshopproject.enums.RequestStatus;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.GraderRequestServiceImpl;
import com.workshop.workshopproject.service.RegistrationServiceImpl;
import com.workshop.workshopproject.service.RequestStatusInfoServiceImpl;
import com.workshop.workshopproject.service.UserServiceImpl;
import com.workshop.workshopproject.utils.DateHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class RequestRestController {
    @PersistenceContext
    private EntityManager entityManager;
    private UserServiceImpl userServiceImpl;
    private RegistrationServiceImpl registrationService;
    private GraderRequestServiceImpl graderRequestService;
    private RequestStatusInfoServiceImpl requestStatusInfoService;
    private DateHandler dateHandler;
    private QueryManager queryManager;

    public RequestRestController(UserServiceImpl userServiceImpl, GraderRequestServiceImpl graderRequestService, RegistrationServiceImpl registrationService, QueryManager queryManager, RequestStatusInfoServiceImpl requestStatusInfoService, DateHandler dateHandler) {
        this.userServiceImpl = userServiceImpl;
        this.queryManager = queryManager;
        this.requestStatusInfoService = requestStatusInfoService;
        this.dateHandler = dateHandler;
    }

    @Transactional
    @PostMapping("/grader-request/status")
    public ResponseEntity setRequestStatus(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader) throws JsonProcessingException, Exception {
        try {
            System.out.println("sadsd");
            String token = (String) jsonHeader.get("authorization");
            System.out.println(token);
            User user = queryManager.getUser(token);
            int requestId = (int)jsonBody.get("request_id");
            System.out.println(requestId);
            GraderRequest graderRequest = (GraderRequest) entityManager.createQuery("SELECT graderRequest from GraderRequest graderRequest where graderRequest.id = :requestId")
                    .setParameter("requestId", requestId)
                    .getResultList()
                    .get(0);
//            GraderRequest graderRequest = graderRequestService.findById(4);
            System.out.println(graderRequest);
            Role supervisor;

            if ((supervisor = user.hasRole(Supervisor.class)) == null) {
//                supervisor = new Supervisor();
//                user.getRoles().add(supervisor);
////                return new ResponseEntity<>("User Not Allowed", HttpStatus.METHOD_NOT_ALLOWED);
//                graderRequest.getOfferedWorkshop().setSupervisor((Supervisor) supervisor);
//                entityManager.persist(supervisor);
                return new ResponseEntity<>("NOT ALLOWED", HttpStatus.BAD_REQUEST);
            }
            System.out.println(graderRequest);
            System.out.println(user.getUsername());
            ObjectMapper objectMapper = new ObjectMapper();
            String requestStatusInfoBody = objectMapper.writeValueAsString(jsonBody.get("request_info"));
            System.out.println(jsonBody.get("request_info"));
            Map<String, Object> requestInfo = (Map<String, Object>)  jsonBody.get("request_info");
//            RequestStatusInfo requestStatusInfo = objectMapper.readValue(requestStatusInfoBody, RequestStatusInfo.class);
//            BeanUtils.copyProperties(requestStatusInfo, graderRequest.getRequestStatusInfo(), "id");
            graderRequest.getRequestStatusInfo().setInfo((String)requestInfo.get("info"));
            graderRequest.getRequestStatusInfo().setRequestStatus(RequestStatus.valueOf((String)requestInfo.get("requestStatus")));
            graderRequest.getRequestStatusInfo().setResponseDate(dateHandler.getCurrentDate());
            System.out.println(graderRequest.getRequestStatusInfo().getRequestStatus());
            entityManager.merge(graderRequest);
//            graderRequestService.save(graderRequest);
            return new ResponseEntity<>("Successful", HttpStatus.OK );
        } catch (Exception exc) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    @PostMapping("/grader-request/role") // CONTINUE
    public ResponseEntity defineGraderRole(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader) {
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        if (user.hasRole(Supervisor.class) == null) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Permission Not Given");
        }
        int requestId = (int)jsonBody.get("request_id");
        GraderRequest graderRequest = (GraderRequest) entityManager.createQuery("SELECT graderRequest from GraderRequest graderRequest where graderRequest.id = :requestId")
                .setParameter("requestId", requestId)
                .getResultList()
                .get(0);
        if (graderRequest.getRequestStatusInfo().getRequestStatus() != RequestStatus.Accepted) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("The Request is Not Accepted Yet!");
        }
        GraderWorkshopRelation graderWorkshopRelation = new GraderWorkshopRelation();
        graderWorkshopRelation.setGraderRequest(graderRequest);
        GraderType graderType = GraderType.valueOf((String)jsonBody.get("grader_type"));
        graderWorkshopRelation.setGraderType(graderType);
        entityManager.persist(graderWorkshopRelation);
        return ResponseEntity.status(HttpStatus.OK).body("Done!");
    }


}
