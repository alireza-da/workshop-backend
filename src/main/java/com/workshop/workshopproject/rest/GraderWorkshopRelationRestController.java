package com.workshop.workshopproject.rest;


import com.workshop.workshopproject.entity.GraderWorkshopRelation;
import com.workshop.workshopproject.entity.StudentGroup;
import com.workshop.workshopproject.service.GraderWorkshopRelationService;
import com.workshop.workshopproject.service.StudentGroupService;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Map;


@RestController
@RequestMapping("api/")
public class GraderWorkshopRelationRestController {
    private EntityManager entityManager;
    private GraderWorkshopRelationService graderWorkshopRelationService;
    private StudentGroupService studentGroupService;


    public GraderWorkshopRelationRestController(EntityManager entityManager, GraderWorkshopRelationService graderWorkshopRelationService) {
        this.entityManager = entityManager;
        this.graderWorkshopRelationService = graderWorkshopRelationService;
    }

    @GetMapping("findGraderWorkshopRelation")
    public GraderWorkshopRelation findById(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader){
        int id = (int) jsonBody.get("GraderWorkshopRelation_id");
        return graderWorkshopRelationService.findById(id);
    }

    @Transactional
    @PostMapping("saveGraderWorkshopRelation")
    public GraderWorkshopRelation save(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader){
        GraderWorkshopRelation graderWorkshopRelation = new GraderWorkshopRelation();
        int studentGpId = (int) jsonBody.get("student_group_id");
        StudentGroup studentGroup = studentGroupService.findById(studentGpId);
        int graderReqId = (int) jsonBody.get("grader_request_id");

        return graderWorkshopRelation;

    }

    @DeleteMapping("deleteGraderWorkshopRelation")
    public void deleteById(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader){
        graderWorkshopRelationService.deleteById((int) jsonBody.get("GraderWorkshopRelation_id"));
    }
}
