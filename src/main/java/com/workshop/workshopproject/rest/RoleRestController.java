package com.workshop.workshopproject.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.workshopproject.entity.Manager;
import com.workshop.workshopproject.entity.Role;
import com.workshop.workshopproject.entity.User;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.RoleServiceImpl;
import com.workshop.workshopproject.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class RoleRestController {
    private QueryManager queryManager;
    private RoleServiceImpl roleServiceImpl;
    private UserServiceImpl userServiceImpl;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public RoleRestController(QueryManager queryManager, RoleServiceImpl roleServiceImpl, UserServiceImpl userServiceImpl) {
        this.queryManager = queryManager;
        this.roleServiceImpl = roleServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @CrossOrigin("*")
    @GetMapping("/roles")
    public List<Role> findAll() {
        return roleServiceImpl.findAll();
    }

    @RequestMapping(value = "/roles/{roleId}", method = RequestMethod.GET)
    @GetMapping("/roles/{roleId}")
    public Role getRole(@PathVariable int roleId) throws RuntimeException {
        try {
            Role role = roleServiceImpl.findById(roleId);

            if (role == null) {
                throw new CustomException("Role id not found - " + roleId);
            }
            return role;
        } catch (Exception exc) {
            throw new RuntimeException();
        }
    }

    @Transactional
    @PostMapping("/roles")
    public ResponseEntity addRole(@RequestHeader Map<String, Object> jsonHeader, @RequestBody Map<String, Object> jsonBody) {
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        Role userRole;
        if ((userRole = user.hasRole(Manager.class)) == null ) {
            userRole = new Manager();
            userRole.setUser(user);
            user.getRoles().add(userRole);
            entityManager.persist(userRole);
        }
        User targetUser = userServiceImpl.findByUsername((String) jsonBody.get("username"));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> roleJson = new HashMap<>();
        roleJson.put("title", jsonBody.get("title"));
        Role role = objectMapper.convertValue(roleJson, Role.class);
        System.out.println(role);
        System.out.println(targetUser.getRoles());
        Role targetUserRole;
        if ((targetUserRole = targetUser.hasRole(role.getClass())) == null) {
            targetUser.getRoles().add(role);
            role.setUser(targetUser);
            entityManager.persist(role);
            return new ResponseEntity<>(role, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(targetUserRole, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin("*")
    @PostMapping("/spec-role")
    public List<Role> findAll(@RequestBody Map<String, Object> jsonBody) throws ClassNotFoundException {
        String roleTitle = (String) jsonBody.get("title");
        roleTitle = Character.toString(roleTitle.charAt(0)).toUpperCase() + roleTitle.substring(1);
        Class cls = Class.forName("com.workshop.workshopproject.entity." + roleTitle);
        List<Role> roles = new ArrayList<>();
        List<Role> allRoles = roleServiceImpl.findAll();
        for (Role role : allRoles) {
            if (role.getClass() == cls) {
                roles.add(role);
            }
        }
        return roles;
    }
}
