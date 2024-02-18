package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.WorkshopFormRelation;
import com.workshop.workshopproject.service.WorkshopFormRelationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkshopFormRelationRestController {
    private WorkshopFormRelationServiceImpl workshopFormRelationService;

    @Autowired
    public WorkshopFormRelationRestController(WorkshopFormRelationServiceImpl workshopFormRelationService) {
        this.workshopFormRelationService = workshopFormRelationService;
    }

    // expose "/employees" and return list of employees
    @CrossOrigin(origins = "*")
    @GetMapping("/workshop_form_relations")
    public List<WorkshopFormRelation> findAll() {
        return workshopFormRelationService.findAll();
    }

    // add mapping for GET /employees/{employeeId}

    @RequestMapping(value = "/workshop_form_relations/{workshopFormRelationId}", method = RequestMethod.GET)
    @GetMapping("/workshop_form_relations/{workshopFormRelationId}")
    public WorkshopFormRelation getContactPoint(@PathVariable int workshopFormRelationId) throws RuntimeException {
        try {
            WorkshopFormRelation contactPoint = workshopFormRelationService.findById(workshopFormRelationId);

            if (contactPoint == null) {
                throw new CustomException("Workshop form relation id not found - " + workshopFormRelationId);
            }

            return contactPoint;
        } catch (Exception exc) {
            throw new RuntimeException();
        }


    }

    // add mapping for POST /employees - add new employee

    @PostMapping("/workshop_form_relations")
    public WorkshopFormRelation addContactPoint(@RequestBody(required = false) WorkshopFormRelation contactPoint) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        contactPoint.setId(0);

        workshopFormRelationService.save(contactPoint);

        return contactPoint;
    }

    // add mapping for PUT /employees - update existing employee

    @PutMapping("/workshop_form_relations")
    public WorkshopFormRelation updateContactPoint(@RequestBody WorkshopFormRelation contactPoint) {

        workshopFormRelationService.save(contactPoint);

        return contactPoint;
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee

    @DeleteMapping("/workshop_form_relations/{workshopFormRelationsId}")
    public String deleteContactPoint(@PathVariable int workshopFormRelationsId) {

        WorkshopFormRelation contactPoint = workshopFormRelationService.findById(workshopFormRelationsId);

        // throw exception if null

        if (contactPoint == null) {
            throw new RuntimeException("Contact Point id not found - " + workshopFormRelationsId);
        }

        workshopFormRelationService.deleteById(workshopFormRelationsId);

        return "Deleted Contact Point id - " + workshopFormRelationsId;
    }
}
