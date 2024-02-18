package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.Student;
import com.workshop.workshopproject.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class StudentRestController {
    private StudentServiceImpl studentServiceImpl;

    @Autowired
    public StudentRestController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/students")
    public List<Student> findAll() {
        return studentServiceImpl.findAll();
    }

    // add mapping for GET /employees/{employeeId}

    @RequestMapping(value = "/students/{studentId}", method = RequestMethod.GET)
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) throws RuntimeException {
        try {
            Student student = studentServiceImpl.findById(studentId);

            if (student == null) {
                throw new NotFoundException("Student id not found - " + studentId);
            }

            return student;
        } catch (Exception exc) {
            throw new RuntimeException();
        }
    }

    // add mapping for POST /employees - add new employee

    @PostMapping("/students")
    public Student addStudent(@RequestBody(required = false) Student student) throws NoSuchAlgorithmException {

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
        student.setId(0);


        studentServiceImpl.save(student);

        return student;
    }

    // add mapping for PUT /employees - update existing employee

    @PutMapping("/students")
    public Student updateUser(@RequestBody Student student) {

        studentServiceImpl.save(student);

        return student;
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee

    @DeleteMapping("/students/{studentId}")
    public String deleteUser(@PathVariable int studentId) {

        Student student = studentServiceImpl.findById(studentId);

        // throw exception if null

        if (student == null) {
            throw new RuntimeException("Student id not found - " + studentId);
        }

        studentServiceImpl.deleteById(studentId);

        return "Deleted employee id - " + studentId;
    }
}
