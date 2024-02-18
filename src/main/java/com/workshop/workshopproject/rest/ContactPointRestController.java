package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.ContactPoint;
import com.workshop.workshopproject.service.ContactPointServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/")
public class ContactPointRestController {
    private ContactPointServiceImpl contactPointService;

    @Autowired
    public ContactPointRestController(ContactPointServiceImpl contactPointService) {
        this.contactPointService = contactPointService;
    }

    // expose "/employees" and return list of employees
    @CrossOrigin(origins = "*")
    @GetMapping("/contactPoints")
    public List<ContactPoint> findAll() {
        return contactPointService.findAll();
    }

    // add mapping for GET /employees/{employeeId}

    @RequestMapping(value = "/contactPoints/{contactPointId}", method = RequestMethod.GET)
    @GetMapping("/contactPoints/{contactPointId}")
    public ContactPoint getContactPoint(@PathVariable int contactPointId) throws RuntimeException {
        try {
            ContactPoint contactPoint = contactPointService.findById(contactPointId);

            if (contactPoint == null) {
                throw new CustomException("Contact Point id not found - " + contactPointId);
            }

            return contactPoint;
        } catch (Exception exc) {
            throw new RuntimeException();
        }


    }

    // add mapping for POST /employees - add new employee

    @PostMapping("/contactPoints")
    public ContactPoint addContactPoint(@RequestBody(required = false) ContactPoint contactPoint) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        contactPoint.setId(0);

        contactPointService.save(contactPoint);

        return contactPoint;
    }

    // add mapping for PUT /employees - update existing employee

    @PutMapping("/contactPoints")
    public ContactPoint updateContactPoint(@RequestBody ContactPoint contactPoint) {

        contactPointService.save(contactPoint);

        return contactPoint;
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee

    @DeleteMapping("/contactPoints/{contactPointId}")
    public String deleteContactPoint(@PathVariable int contactPointId) {

        ContactPoint contactPoint = contactPointService.findById(contactPointId);

        // throw exception if null

        if (contactPoint == null) {
            throw new RuntimeException("Contact Point id not found - " + contactPointId);
        }

        contactPointService.deleteById(contactPointId);

        return "Deleted Contact Point id - " + contactPointId;
    }
}
