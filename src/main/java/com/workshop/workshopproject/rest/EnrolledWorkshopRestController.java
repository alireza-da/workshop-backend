package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.EnrolledWorkshopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EnrolledWorkshopRestController {
    private EnrolledWorkshopServiceImpl enrolledWorkshopServiceImpl;
    private QueryManager queryManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public EnrolledWorkshopRestController(EnrolledWorkshopServiceImpl enrolledWorkshopServiceImpl, QueryManager queryManager) {
        this.enrolledWorkshopServiceImpl = enrolledWorkshopServiceImpl;
        this.queryManager = queryManager;
    }

    @GetMapping("/enrolled-workshops")
    public List<EnrolledWorkshop> findAll() {
        return enrolledWorkshopServiceImpl.findAll();
    }

    @RequestMapping(value = "/enrolled-workshops/{enrolledWorkshopId}", method = RequestMethod.GET)
    @GetMapping("/enrolled-workshops/{enrolledWorkshopId}")
    public EnrolledWorkshop getEnrolledWorkshop(@PathVariable int enrolledWorkshopId) throws RuntimeException {
        try {
            EnrolledWorkshop enrolledWorkshop = enrolledWorkshopServiceImpl.findById(enrolledWorkshopId);

            if (enrolledWorkshop == null) {
                throw new NotFoundException("Enrolled Workshop id not found - " + enrolledWorkshopId);
            }
            return enrolledWorkshop;
        } catch (Exception exc) {
            throw new RuntimeException();
        }

    }

//    @PostMapping(value = "/enrolled-workshops")
//    public EnrolledWorkshop addEnrolledWorkshop(@RequestBody(required = false) EnrolledWorkshop enrolledWorkshop) throws NoSuchAlgorithmException {
//
//        // also just in case they pass an id in JSON ... set id to 0
//        // this is to force a save of new item ... instead of update
//        enrolledWorkshop.setId(0);
//
//        enrolledWorkshopServiceImpl.save(enrolledWorkshop);
//
//        return enrolledWorkshop;
//    }

    @PostMapping(value = "/enrolled-workshops")
    public void addEnrolledWorkshop(@RequestBody(required = false) Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader) {
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        Role accountant;
        if ((accountant = user.hasRole(Accountant.class)) == null) {
            return;
        }
        int registrationId = (int) jsonBody.get("registration_id");
        Registration registration = (Registration) entityManager.createQuery("select registration from Registration registration where registration.id = :registrationId")
                .setParameter("registrationId", registrationId)
                .getResultList()
                .get(0);
        EnrolledWorkshop enrolledWorkshop = new EnrolledWorkshop();
        enrolledWorkshop.setRegistration(registration);
        entityManager.persist(enrolledWorkshop);
    }

    @PutMapping("/enrolled-workshops")
    public EnrolledWorkshop updateEnrolledWorkshop(@RequestBody EnrolledWorkshop enrolledWorkshop, @RequestHeader Map<String, Object> jsonHeader) {
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        Role accountant;
        if ((accountant = user.hasRole(Accountant.class)) == null) {
            throw new RuntimeException("Permission Denied");
        }
        enrolledWorkshopServiceImpl.save(enrolledWorkshop);

        return enrolledWorkshop;
    }


    @DeleteMapping("/enrolled-workshops/{enrolledWorkshopId}")
    public String deleteEnrolledWorkshop(@PathVariable int enrolledWorkshopId,@RequestBody(required = false) Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader) {
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        Role accountant;
        if ((accountant = user.hasRole(Accountant.class)) == null) {
            return "Permission Denied";
        }
        EnrolledWorkshop workshop = enrolledWorkshopServiceImpl.findById(enrolledWorkshopId);

        // throw exception if null

        if (workshop == null) {
            throw new RuntimeException("Enrolled Workshop id not found - " + enrolledWorkshopId);
        }

        enrolledWorkshopServiceImpl.deleteById(enrolledWorkshopId);

        return "Deleted enrolled workshop id - " + enrolledWorkshopId;
    }
}
