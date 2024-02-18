package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.enums.ContactType;
import com.workshop.workshopproject.enums.Gender;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintDeclarationException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/")
public class UserRestController {
    private UserServiceImpl userServiceImpl;
    private QueryManager queryManager;
    private RestExceptionHandler exceptionHandler;
    //    private StudentServiceImpl studentServiceImpl;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserRestController(UserServiceImpl userServiceImpl, QueryManager queryManager, RestExceptionHandler exceptionHandler) {
        this.userServiceImpl = userServiceImpl;
        this.queryManager = queryManager;
        this.exceptionHandler = exceptionHandler;
//        this.studentServiceImpl = studentServiceImpl;
    }

    // expose "/employees" and return list of employees
    @CrossOrigin(origins = "*")
    @GetMapping("/users")
    public List<User> findAll() {
        return userServiceImpl.findAll();
    }

    // add mapping for GET /employees/{employeeId}

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable String userId) throws RuntimeException {
        try {
            User theUser = userServiceImpl.findById(userId);

            if (theUser == null) {
                throw new CustomException("User id not found - " + userId);
            }

            return theUser;
        } catch (Exception exc) {
            throw new RuntimeException();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/users/info")
    public Map<String, Object> getUser(@RequestHeader Map<String, Object> jsonHeader) {
        System.out.println(jsonHeader);
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        return response;

    }

    // add mapping for POST /employees - add new employee

    @CrossOrigin(origins = "*")
    @Transactional
    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody User theUser, Errors errors) throws NoSuchAlgorithmException, ConstraintDeclarationException, MethodArgumentNotValidException, Exception {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
//        if (errors.hasErrors()) {
//            System.out.println(errors.getFieldError());
////            throw new RuntimeException(error);
//
//        }

        if (errors.hasErrors()) {
            Map<String, Object> body = new HashMap<>();
            Map<String, Object> errorMap = new HashMap<>();
            body.put("timestamp", new Date());
            body.put("status", HttpStatus.BAD_REQUEST);
            errors.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errorMap.put(fieldName, errorMessage);
            });
            body.put("errors", errorMap);
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        System.out.println("dhsjfgbdsbgfsd");

//        Student student = new Student();
//        studentServiceImpl.save(student);

//        List<Role> roles = entityManager.createQuery("SELECT role FROM Role role where role.id = 1").getResultList();
//        ArrayList<Role> roles = new ArrayList<>();
//        roles.add(student);
//        theUser.setRoles(roles);
        //        System.out.println(theUser.getRoles());
//        System.out.println(theUser);
//        theUser.setId(0);

//        theUser.setPassword(new BCryptPasswordEncoder().encode(theUser.getPassword()));

        Role student = new Student();
        Role grader = new Grader();
        entityManager.persist(student);
        entityManager.persist(grader);
        theUser.getRoles().add(student);
        theUser.getRoles().add(grader);
        student.setUser(theUser);
        grader.setUser(theUser);
        for (ContactPoint contactPoint : theUser.getContactPoints()) {
            contactPoint.setUser(theUser);
        }
        userServiceImpl.save(theUser);

        return new ResponseEntity(HttpStatus.OK);
    }



    // add mapping for PUT /employees - update existing employee

    @Transactional
    @PatchMapping("/users")
    public User changeUser(@RequestBody User theUser) {
        userServiceImpl.save(theUser);
        return theUser;
    }

    @Transactional
    @PutMapping("/users")
    public ResponseEntity updateUser(@RequestHeader Map<String, Object> jsonHeader, @RequestBody Map<String, Object> jsonBody) throws ParseException {
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        String firstName = (String) jsonBody.get("firstName");
        String lastName = (String) jsonBody.get("lastName");
        String email = (String) jsonBody.get("email");
        String birthDate = (String) jsonBody.get("birthDate");
        String phoneNumber = (String) jsonBody.get("phoneNumber");
        String address = (String) jsonBody.get("address");
        String gender = (String) jsonBody.get("gender");
        String password = (String) jsonBody.get("password");
        String imageUrl = (String) jsonBody.get("imageUrl");
        if (firstName != null) {
            user.setFirstName(firstName);
        } else if (lastName != null && !lastName.isEmpty()) {
            user.setLastName(lastName);
        } else if (email != null && !email.isEmpty()) {
            user.updateContactPoint(ContactType.Email, email);
        } else if (address != null && !address.isEmpty()) {
            user.updateContactPoint(ContactType.Address, address);
        } else if (birthDate != null && !birthDate.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(birthDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            user.setBirthDate(calendar);
        } else if (phoneNumber != null && !phoneNumber.isEmpty()) {
            user.updateContactPoint(ContactType.Phone, phoneNumber);
        } else if (gender != null && !gender.isEmpty()) {
            user.setGender(Gender.valueOf(gender));
        } else if (password != null && !password.isEmpty()) {
            user.setPassword(new BCryptPasswordEncoder().encode(password));
        } else if (imageUrl != null && !imageUrl.isEmpty()) {
            user.setImageUrl(imageUrl);
        }
        entityManager.merge(user);
        return new ResponseEntity<>("User Successfully Updated", HttpStatus.OK);
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable String userId) {

        User tempEmployee = userServiceImpl.findById(userId);

        // throw exception if null

        if (tempEmployee == null) {
            throw new RuntimeException("Employee id not found - " + userId);
        }

        userServiceImpl.deleteById(userId);

        return "Deleted employee id - " + userId;
    }
}
