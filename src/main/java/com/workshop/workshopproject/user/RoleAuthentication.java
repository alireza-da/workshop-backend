package com.workshop.workshopproject.user;

import com.workshop.workshopproject.entity.*;
import com.workshop.workshopproject.queries.QueryManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@Component
public class RoleAuthentication {

    private QueryManager queryManager;

    public boolean authSupervisor(Map<String, Object> jsonHeader) {
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        Role supervisor;
        if ((supervisor = user.hasRole(Supervisor.class)) == null) {
            return false;
        }
        return true;
    }

    public boolean authAccountant(@RequestHeader Map<String, Object> jsonHeader){
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        Role accountant;
        if ((accountant = user.hasRole(Accountant.class)) == null) {
            return false;
        }
        return true;
    }

    public boolean authGrader(@RequestHeader Map<String, Object> jsonHeader){
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        Role grader;
        if ((grader = user.hasRole(Grader.class)) == null) {
            return false;
        }
        return true;
    }

    public boolean authStudent(@RequestHeader Map<String, Object> jsonHeader){
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        Role student;
        if ((student = user.hasRole(Student.class)) == null) {
            return false;
        }
        return true;
    }

    public boolean authAdmin(@RequestHeader Map<String, Object> jsonHeader){
        String token = (String) jsonHeader.get("authorization");
        User user = queryManager.getUser(token);
        Role manager;
        if ((manager = user.hasRole(Manager.class)) == null) {
            return false;
        }
        return true;
    }
}
