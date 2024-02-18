package com.workshop.workshopproject.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.enums.RequestStatus;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.RegistrationServiceImpl;
import com.workshop.workshopproject.utils.DateHandler;
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
public class RegistrationRestController {
    private RegistrationServiceImpl registrationService;
    private QueryManager queryManager;
    private DateHandler dateHandler;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public RegistrationRestController(RegistrationServiceImpl registrationService, QueryManager queryManager, DateHandler dateHandler) {
        this.registrationService = registrationService;
        this.queryManager = queryManager;
        this.dateHandler = dateHandler;
    }

    // expose "/employees" and return list of employees
    @CrossOrigin(origins = "*")
    @GetMapping("/registrations")
    public List<Registration> findAll() {
        return registrationService.findAll();
    }

    // add mapping for GET /employees/{employeeId}

    @RequestMapping(value = "/registrations/{registrationId}", method = RequestMethod.GET)
    @GetMapping("/registrations/{registrationId")
    public Registration getContactPoint(@PathVariable int registrationId) throws RuntimeException {
        try {
            Registration contactPoint = registrationService.findById(registrationId);

            if (contactPoint == null) {
                throw new CustomException("Contact Point id not found - " + registrationId);
            }

            return contactPoint;
        } catch (Exception exc) {
            throw new RuntimeException();
        }

    }

    @Transactional
    @PostMapping("/registrations/status")
    public ResponseEntity setRegistrationStatus(@RequestHeader Map<String, Object> jsonHeader, @RequestBody Map<String, Object> jsonBody) {
        try {
            System.out.println("sadsd");
            String token = (String) jsonHeader.get("authorization");
            System.out.println(token);
            User user = queryManager.getUser(token);
            int registrationId = (int)jsonBody.get("request_id");
            System.out.println(registrationId);
            Registration registration = (Registration) entityManager.createQuery("SELECT registration from Registration registration where registration.id = :registrationId")
                    .setParameter("registrationId", registrationId)
                    .getResultList()
                    .get(0);
//            GraderRequest graderRequest = graderRequestService.findById(4);
            System.out.println(registration);
            Role accountant;

            if ((accountant = user.hasRole(Accountant.class)) == null) {
//                supervisor = new Supervisor();
//                user.getRoles().add(supervisor);
////                return new ResponseEntity<>("User Not Allowed", HttpStatus.METHOD_NOT_ALLOWED);
//                graderRequest.getOfferedWorkshop().setSupervisor((Supervisor) supervisor);
//                entityManager.persist(supervisor);
                return new ResponseEntity<>("NOT ALLOWED", HttpStatus.BAD_REQUEST);
            }
            System.out.println(registration);
            System.out.println(user.getUsername());
            ObjectMapper objectMapper = new ObjectMapper();
            String requestStatusInfoBody = objectMapper.writeValueAsString(jsonBody.get("request_info"));
            System.out.println(jsonBody.get("request_info"));
            Map<String, Object> requestInfo = (Map<String, Object>)  jsonBody.get("request_info");
//            RequestStatusInfo requestStatusInfo = objectMapper.readValue(requestStatusInfoBody, RequestStatusInfo.class);
//            BeanUtils.copyProperties(requestStatusInfo, graderRequest.getRequestStatusInfo(), "id");
            registration.getRequestStatusInfo().setInfo((String)requestInfo.get("info"));
            registration.getRequestStatusInfo().setRequestStatus(RequestStatus.valueOf((String)requestInfo.get("requestStatus")));
            registration.getRequestStatusInfo().setResponseDate(dateHandler.getCurrentDate());
            System.out.println(registration.getRequestStatusInfo().getRequestStatus());
            EnrolledWorkshop enrolledWorkshop = new EnrolledWorkshop();
            enrolledWorkshop.setRegistration(registration);
            registration.setEnrolledWorkshop(enrolledWorkshop);
            entityManager.persist(enrolledWorkshop);
            entityManager.merge(registration);

//            graderRequestService.save(graderRequest);
            return new ResponseEntity<>("Successful", HttpStatus.OK );
        } catch (Exception exc) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }


    // add mapping for POST /employees - add new employee

    @PostMapping("/registrations")
    public Registration addContactPoint(@RequestBody(required = false) Registration contactPoint) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        contactPoint.setId(0);

        registrationService.save(contactPoint);

        return contactPoint;
    }

    // add mapping for PUT /employees - update existing employee

    @PutMapping("/registrations")
    public Registration updateContactPoint(@RequestBody Registration contactPoint) {

        registrationService.save(contactPoint);

        return contactPoint;
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee

    @DeleteMapping("/registrations/{registrationId}")
    public String deleteContactPoint(@PathVariable int registrationId) {

        Registration contactPoint = registrationService.findById(registrationId);

        // throw exception if null

        if (contactPoint == null) {
            throw new RuntimeException("Contact Point id not found - " + registrationId);
        }

        registrationService.deleteById(registrationId);

        return "Deleted Contact Point id - " + registrationId;
    }
}
