package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.Supervisor;
import com.workshop.workshopproject.service.SupervisorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class SupervisorRestController {
    private SupervisorServiceImpl supervisorService;

    @Autowired
    public SupervisorRestController(SupervisorServiceImpl supervisorService) {
        this.supervisorService = supervisorService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/supervisors")
    public List<Supervisor> findAll() {
        return supervisorService.findAll();
    }

    // add mapping for GET /employees/{employeeId}

    @RequestMapping(value = "/supervisors/{supervisorId}", method = RequestMethod.GET)
    @GetMapping("/supervisors/{supervisorId}")
    public Supervisor getSupervisor(@PathVariable int supervisorId) throws RuntimeException {
        try {
            Supervisor supervisor = supervisorService.findById(supervisorId);

            if (supervisor == null) {
                throw new CustomException("Student id not found - " + supervisorId);
            }

            return supervisor;
        } catch (Exception exc) {
            throw new RuntimeException();
        }
    }

    // add mapping for POST /employees - add new employee

    @PostMapping("/supervisors")
    public Supervisor addSupervisor(@RequestBody(required = false) Supervisor supervisor) throws NoSuchAlgorithmException {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
//        Student student = new Student();
//        studentServiceImpl.save(student);

//        List<Role> roles = entityManager.createQuery("SELECT role FROM Role role where role.id = 1").getResultList();
//        ArrayList<Role> roles = new ArrayList<>();
//        roles.add(student);
//        theUser.setRoles(roles);
        //        System.out.println(theUser.getRoles());
//        System.out.println(theUser);
        supervisor.setId(0);


        supervisorService.save(supervisor);

        return supervisor;
    }

    // add mapping for PUT /employees - update existing employee

    @PutMapping("/supervisors")
    public Supervisor updateUser(@RequestBody Supervisor supervisor) {

        supervisorService.save(supervisor);

        return supervisor;
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee

    @DeleteMapping("/supervisors/{supervisorId}")
    public String deleteUser(@PathVariable int supervisorId) {

        Supervisor supervisor = supervisorService.findById(supervisorId);

        // throw exception if null

        if (supervisor == null) {
            throw new RuntimeException("Supervisor id not found - " + supervisorId);
        }

        supervisorService.deleteById(supervisorId);

        return "Deleted supervisor id - " + supervisorId;
    }
}
