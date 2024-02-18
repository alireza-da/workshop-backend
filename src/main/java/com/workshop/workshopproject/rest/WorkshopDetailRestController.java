package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.WorkshopDetail;
import com.workshop.workshopproject.service.WorkshopDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/")
public class WorkshopDetailRestController {
    private WorkshopDetailServiceImpl workshopDetailServiceImpl;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public WorkshopDetailRestController(WorkshopDetailServiceImpl workshopDetailServiceImpl) {
        this.workshopDetailServiceImpl = workshopDetailServiceImpl;
    }

    @GetMapping("/workshopDetails")
    public List<WorkshopDetail> findAll() {
        return workshopDetailServiceImpl.findAll();
    }

//    @RequestMapping(value = "/workshopDetail/{workshopDetailId}", method = RequestMethod.GET)
    @GetMapping("/workshopDetails/{workshopDetailId}")
    public WorkshopDetail getWorkshopDetail(@PathVariable int workshopDetailId) throws RuntimeException {
        try {
            WorkshopDetail workshopDetail = workshopDetailServiceImpl.findById(workshopDetailId);

            if (workshopDetail == null) {
                throw new CustomException("Workshop detail id not found - " + workshopDetailId);
            }
            return workshopDetail;
        } catch (Exception exc) {
            throw new RuntimeException();
        }

    }

    @PostMapping("/workshopDetails")
    public WorkshopDetail addWorkshopDetail(@RequestBody(required = false) WorkshopDetail workshopDetail) throws NoSuchAlgorithmException {
        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        workshopDetail.setId(0);

        workshopDetailServiceImpl.save(workshopDetail);

        return workshopDetail;
    }

    @PostMapping

    @PutMapping("/workshopDetails")
    public WorkshopDetail updateWorkshopDetail(@RequestBody WorkshopDetail workshopDetail) {

        workshopDetailServiceImpl.save(workshopDetail);

        return workshopDetail;
    }

    @DeleteMapping("/workshopDetails/{workshopDetailId}")
    public String deleteWorkshopDetail(@PathVariable int workshopDetailId) {

        WorkshopDetail workshopDetail = workshopDetailServiceImpl.findById(workshopDetailId);

        // throw exception if null

        if (workshopDetail == null) {
            throw new RuntimeException("Workshop id not found - " + workshopDetail);
        }

        workshopDetailServiceImpl.deleteById(workshopDetailId);

        return "Deleted workshop detail id - " + workshopDetail;
    }
}
