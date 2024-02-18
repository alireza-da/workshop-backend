package com.workshop.workshopproject.queries;

import com.workshop.workshopproject.config.JwtTokenUtil;
import com.workshop.workshopproject.entity.OfferedWorkshop;
import com.workshop.workshopproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class QueryManager {
    @PersistenceContext
    private EntityManager entityManager;

    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public QueryManager(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public User getUser(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = (User) entityManager.createQuery("SELECT user from User user where user.username = :username")
                .setParameter("username", username)
                .getResultList()
                .get(0);
        return user;
    }
}
