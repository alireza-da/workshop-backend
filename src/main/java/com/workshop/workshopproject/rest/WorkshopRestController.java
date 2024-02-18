package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.enums.WorkshopRelation;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.WorkshopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class WorkshopRestController {
    private WorkshopServiceImpl workshopServiceImpl;
    @PersistenceContext
    private EntityManager entityManager;

    private QueryManager queryManager;


    @Autowired
    public WorkshopRestController(WorkshopServiceImpl workshopServiceImpl, QueryManager queryManager) {
        this.workshopServiceImpl = workshopServiceImpl;
        this.queryManager = queryManager;
    }

    @GetMapping("/workshops")
    public List<Workshop> findAll() {
        return workshopServiceImpl.findAll();
    }

    @RequestMapping(value = "/workshops/{workshopId}", method = RequestMethod.GET)
    @GetMapping("/workshops/{workshopId}")
    public Workshop getWorkshop(@PathVariable int workshopId) throws RuntimeException {
        try {
            Workshop workshop = workshopServiceImpl.findById(workshopId);

            if (workshop == null) {
                throw new CustomException("Workshop id not found - " + workshopId);
            }
            return workshop;
        } catch (Exception exc) {
            throw new RuntimeException();
        }

    }

    @Transactional
    @PostMapping("/workshops")
    public ResponseEntity addWorkshop(@RequestHeader Map<String, Object> jsonHeader , @RequestBody(required = false)Map<String, Object> jsonBody) throws NoSuchAlgorithmException {
        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        String token = (String) jsonHeader.get("authorization");
        System.out.println(jsonHeader);
        Role role;
        User user = queryManager.getUser(token);
        System.out.println(user.getUsername());
        if ((role = user.hasRole(Manager.class)) == null) {
            role = new Manager();
            user.getRoles().add(role);
            entityManager.persist(role);
//            System.out.println("NOT ALLOWED");
//            return new ResponseEntity<>("Not Allowed", HttpStatus.BAD_REQUEST);
        }
        String title = (String) jsonBody.get("title");
        String description = (String) jsonBody.get("description");
        String imageUrl = (String) jsonBody.get("imageUrl");
        if (workshopServiceImpl.existsByTitle(title)){

            throw new ValidationException("Workshop already existed");

        }

        Workshop workshop = new Workshop();
        workshop.setTitle(title);
        workshop.setDescription(description);
        workshop.setImageUrl(imageUrl);
        workshop.setManager((Manager)role);
        List<Map<String, Object>> workshopDetails;
        workshopDetails = (ArrayList) jsonBody.get("workshopDetails");
        workshop.setId(0);
        if (workshopDetails == null || workshopDetails.isEmpty()) {
            workshopServiceImpl.save(workshop);
        } else {
            for (int index = 0; index < workshopDetails.size(); ++index) {
                WorkshopDetail workshopDetail = new WorkshopDetail();
                Workshop relatedWorkshop = workshopServiceImpl.findById((int)workshopDetails.get(index).get("id"));
                workshopDetail.setRelatedWorkshop(relatedWorkshop);
                WorkshopRelation workshopRelation = WorkshopRelation.valueOf((String)(workshopDetails.get(index).get("workshopRelation")));
                workshopDetail.setWorkshopRelation(workshopRelation);
                workshopDetail.setWorkshop(workshop);
                entityManager.persist(workshopDetail);
//            workshop.getWorkshopDetails().add(workshopDetail);
            }
        }
//        System.out.println("sdfsd");
        return new ResponseEntity<>("Workshop Created!", HttpStatus.OK);

//        workshopServiceImpl.save(workshop);
    }

    @Transactional
    @PatchMapping("/workshops/{workshopId}")
    public Workshop updateWorkshop(@RequestBody Map<String, Object> jsonBody, @PathVariable int workshopId) {
        Workshop workshop = workshopServiceImpl.findById(workshopId);
        if (jsonBody.containsKey("title")) {
            workshop.setTitle((String) jsonBody.get("title"));
        } if (jsonBody.containsKey("description")) {
            workshop.setDescription((String) jsonBody.get("description"));
        } if (jsonBody.containsKey("imageUrl")) {
            workshop.setImageUrl((String)jsonBody.get("imageUrl"));
        } if ((jsonBody.containsKey("workshopDetails"))) {
            List<Map<String, Object>> workshopDetails = (ArrayList) jsonBody.get("workshopDetails");
            for (int index = 0; index < workshopDetails.size(); ++index) {
                WorkshopDetail workshopDetail = new WorkshopDetail();
                Workshop relatedWorkshop = workshopServiceImpl.findById((int)workshopDetails.get(index).get("id"));
                workshopDetail.setRelatedWorkshop(relatedWorkshop);
                WorkshopRelation workshopRelation = WorkshopRelation.valueOf((String)(workshopDetails.get(index).get("workshopRelation")));
                workshopDetail.setWorkshopRelation(workshopRelation);
                workshopDetail.setWorkshop(workshop);
                entityManager.persist(workshopDetail);
            }
        }

        return workshop;
    }

    @DeleteMapping("/workshops/{workshopId}")
    public String deleteWorkshop(@PathVariable int workshopId) {

        Workshop workshop = workshopServiceImpl.findById(workshopId);

        // throw exception if null

        if (workshop == null) {
            throw new RuntimeException("Workshop id not found - " + workshopId);
        }

        workshopServiceImpl.deleteById(workshopId);

        return "Deleted workshop id - " + workshopId;
    }
}
