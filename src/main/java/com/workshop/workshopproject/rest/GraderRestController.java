package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.Grader;
import com.workshop.workshopproject.entity.GraderWorkshopRelation;
import com.workshop.workshopproject.entity.OfferedWorkshop;
import com.workshop.workshopproject.service.GraderServiceImpl;
import com.workshop.workshopproject.service.OfferedWorkshopService;
import com.workshop.workshopproject.service.OfferedWorkshopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/")
public class GraderRestController {
    private GraderServiceImpl graderService;
    private OfferedWorkshopServiceImpl offeredWorkshopService;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public GraderRestController(GraderServiceImpl graderService, OfferedWorkshopServiceImpl offeredWorkshopService) {
        this.graderService = graderService;
        this.offeredWorkshopService = offeredWorkshopService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/graders")
    public List<Grader> findAll() {
        return graderService.findAll();
    }

    // add mapping for GET /employees/{employeeId}

    @RequestMapping(value = "/graders/{graderId}", method = RequestMethod.GET)
    @GetMapping("/graders/{graderId}")
    public Grader getGrader(@PathVariable int graderId) throws RuntimeException {
        try {
            Grader supervisor = graderService.findById(graderId);

            if (supervisor == null) {
                throw new CustomException("Accountant id not found - " + graderId);
            }

            return supervisor;
        } catch (Exception exc) {
            throw new RuntimeException();
        }
    }

    @Transactional
    @PostMapping("/graders/role")
    public ResponseEntity getGraderRole(@RequestBody Map<String, Object> jsonBody) {
        int workshopId = (int) jsonBody.get("workshop_id");
        int graderId = (int) jsonBody.get("grader_id");
        OfferedWorkshop offeredWorkshop = (OfferedWorkshop) entityManager.createQuery("SELECT workshop from OfferedWorkshop workshop where workshop.id = :workshopId")
                .setParameter("workshopId", workshopId)
                .getResultList()
                .get(0);
        Grader grader = (Grader) entityManager.createQuery("SELECT grader from Grader grader where grader.id = :graderId")
                .setParameter("graderId", graderId)
                .getResultList()
                .get(0);
        GraderWorkshopRelation graderWorkshopRelation = offeredWorkshop.findGraderRole(grader);
        if (graderWorkshopRelation == null) {
            System.out.println("sdsd");
            return new ResponseEntity<>("Not Found!", HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> workshopRelation = new HashMap<>();
        workshopRelation.put("id", graderWorkshopRelation.getId());
        workshopRelation.put("role", graderWorkshopRelation.getGraderType());
        return new ResponseEntity<>(workshopRelation, HttpStatus.OK);

    }

    // add mapping for POST /employees - add new employee

    @PostMapping("/graders")
    public Grader addGrader(@RequestBody(required = false) Grader accountant) throws NoSuchAlgorithmException {

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


        graderService.save(accountant);

        return accountant;
    }

    // add mapping for PUT /employees - update existing employee

    @PutMapping("/grader")
    public Grader updateUser(@RequestBody Grader accountant) {

        graderService.save(accountant);

        return accountant;
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee

    @DeleteMapping("/graders/{graderId}")
    public String deleteUser(@PathVariable int graderId) {

        Grader supervisor = graderService.findById(graderId);

        // throw exception if null

        if (supervisor == null) {
            throw new RuntimeException("Accountant id not found - " + graderId);
        }

        graderService.deleteById(graderId);

        return "Deleted Accountant id - " + graderId;
    }
}
