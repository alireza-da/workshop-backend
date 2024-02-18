package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.Accountant;
import com.workshop.workshopproject.service.AccountantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/")
public class AccountantRestController {
    private AccountantServiceImpl accountantService;

    @Autowired
    public AccountantRestController(AccountantServiceImpl accountantService) {
        this.accountantService = accountantService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/accountants")
    public List<Accountant> findAll() {
        return accountantService.findAll();
    }

    // add mapping for GET /employees/{employeeId}

    @RequestMapping(value = "/accountants/{accountantId}", method = RequestMethod.GET)
    @GetMapping("/accountants/{accountantId}")
    public Accountant getSupervisor(@PathVariable int accountantId) throws RuntimeException {
        try {
            Accountant supervisor = accountantService.findById(accountantId);

            if (supervisor == null) {
                throw new CustomException("Accountant id not found - " + accountantId);
            }

            return supervisor;
        } catch (Exception exc) {
            throw new RuntimeException();
        }
    }

    // add mapping for POST /employees - add new employee

    @PostMapping("/accountants")
    public Accountant addSupervisor(@RequestBody(required = false) Accountant accountant) throws NoSuchAlgorithmException {

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
        accountant.setId(0);


        accountantService.save(accountant);

        return accountant;
    }

    // add mapping for PUT /employees - update existing employee

    @PutMapping("/accountants")
    public Accountant updateUser(@RequestBody Accountant accountant) {

        accountantService.save(accountant);

        return accountant;
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee

    @DeleteMapping("/accountants/{accountantId}")
    public String deleteUser(@PathVariable int accountantId) {

        Accountant supervisor = accountantService.findById(accountantId);

        // throw exception if null

        if (supervisor == null) {
            throw new RuntimeException("Accountant id not found - " + accountantId);
        }

        accountantService.deleteById(accountantId);

        return "Deleted Accountant id - " + accountantId;
    }
}
