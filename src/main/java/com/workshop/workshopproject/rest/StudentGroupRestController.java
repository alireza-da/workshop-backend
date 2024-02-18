package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.enums.GraderType;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.EnrolledWorkshopServiceImpl;
import com.workshop.workshopproject.service.GraderRequestServiceImpl;
import com.workshop.workshopproject.service.StudentGroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class StudentGroupRestController {
    private StudentGroupServiceImpl studentGroupServiceImpl;
    private EnrolledWorkshopServiceImpl enrolledWorkshopServiceImpl;
    private StudentGroupServiceImpl getStudentGroupServiceImpl;
    private GraderRequestServiceImpl graderRequestService;

    private QueryManager queryManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public StudentGroupRestController(StudentGroupServiceImpl studentGroupServiceImpl, EnrolledWorkshopServiceImpl enrolledWorkshopServiceImpl, QueryManager queryManager, GraderRequestServiceImpl graderRequestService) {
        this.studentGroupServiceImpl = studentGroupServiceImpl;
        this.enrolledWorkshopServiceImpl = enrolledWorkshopServiceImpl;
        this.queryManager = queryManager;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/groups")
    public List<StudentGroup> findAll() {
        return studentGroupServiceImpl.findAll();
    }

    // add mapping for GET /employees/{employeeId}

    @RequestMapping(value = "/groups/{studentGroupId}", method = RequestMethod.GET)
    @GetMapping("/students/{studentGroupId}")
    public StudentGroup getStudent(@PathVariable int studentGroupId) throws RuntimeException {
        try {
            StudentGroup student = studentGroupServiceImpl.findById(studentGroupId);

            if (student == null) {
                throw new NotFoundException("group id not found - " + studentGroupId);
            }

            return student;
        } catch (Exception exc) {
            throw new RuntimeException();
        }
    }

    // add mapping for POST /employees - add new employee

    @Transactional
    @PostMapping("/groups")
    public ResponseEntity addStudentGroup(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader) throws NoSuchAlgorithmException {

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
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        if (user.hasRole(Supervisor.class) == null) {
            return new ResponseEntity<>("Not Allowed", HttpStatus.BAD_REQUEST);
        }
        int workshopId = (int) jsonBody.get("workshop_id");
        List<Integer> enrolledWorkshopIds = (List<Integer>) jsonBody.get("enrolled_workshop_ids");
        List<Map<String, Object>> graders = (List<Map<String, Object>>) jsonBody.get("graders");
        OfferedWorkshop offeredWorkshop = (OfferedWorkshop) entityManager.createQuery("SELECT offeredWorkshop from OfferedWorkshop offeredWorkshop where offeredWorkshop.id = :workshopId")
                .setParameter("workshopId", workshopId)
                .getResultList()
                .get(0);
        int index;
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setOfferedWorkshop(offeredWorkshop);
        for (index = 0; index < enrolledWorkshopIds.size(); ++index) {
            EnrolledWorkshop enrolledWorkshop = enrolledWorkshopServiceImpl.findById(enrolledWorkshopIds.get(index));
            System.out.println(enrolledWorkshop);
            enrolledWorkshop.setStudentGroup(studentGroup);
            studentGroup.getEnrolledWorkshops().add(enrolledWorkshop);
        }
        System.out.println(studentGroup.getEnrolledWorkshops());
        for (index = 0; index < graders.size(); ++index) {
            Map<String, Object> graderInfo = graders.get(index);
            GraderWorkshopRelation graderWorkshopRelation = new GraderWorkshopRelation();
            graderWorkshopRelation.setGraderType(GraderType.valueOf((String)graderInfo.get("graderType")));
            int requestId = (int) graderInfo.get("request_id");
            GraderRequest graderRequest = (GraderRequest) entityManager.createQuery("SELECT graderRequest from GraderRequest graderRequest where graderRequest.id = :requestId")
                    .setParameter("requestId", requestId)
                    .getResultList()
                    .get(0);
            System.out.println(graderRequest);
            graderWorkshopRelation.setGraderRequest(graderRequest);
            graderWorkshopRelation.setStudentGroup(studentGroup);
            studentGroup.getGraderWorkshopRelations().add(graderWorkshopRelation);
//            entityManager.persist(graderWorkshopRelation);
        }
        System.out.println(studentGroup.getGraderWorkshopRelations());
//        entityManager.persist(studentGroup);
        return new ResponseEntity<>("Group Created", HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/groups/add")
    public void addToGroup(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader) {
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);

        int groupId = (int) jsonBody.get("group_id");
        int enrolledWorkshopId = (int) jsonBody.get("enrolled_workshop_id");
        EnrolledWorkshop enrolledWorkshop = enrolledWorkshopServiceImpl.findById(enrolledWorkshopId);
        Role supervisor;
        if ((supervisor = user.hasRole(Supervisor.class)) == null) {
            System.out.println("entered");
            return;
        }
        StudentGroup studentGroup = studentGroupServiceImpl.findById(groupId);
        enrolledWorkshop.setStudentGroup(studentGroup);
        entityManager.merge(enrolledWorkshop);
    }

    // add mapping for PUT /employees - update existing employee

    @PutMapping("/groups")
    public StudentGroup updateStudentGroup(@RequestBody StudentGroup studentGroup) {

        studentGroupServiceImpl.save(studentGroup);

        return studentGroup;
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee

    @DeleteMapping("/groups/{groupId}")
    public String deleteUser(@PathVariable int groupId) {

        StudentGroup student = studentGroupServiceImpl.findById(groupId);

        // throw exception if null

        if (student == null) {
            throw new RuntimeException("group id not found - " + groupId);
        }

        studentGroupServiceImpl.deleteById(groupId);

        return "Deleted group id - " + groupId;
    }
}
