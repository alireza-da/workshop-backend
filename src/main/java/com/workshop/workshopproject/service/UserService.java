package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> findAll();

    public User findById(String theId);

    public void save(User theEmployee);

    public void deleteById(String theId);

    public User findByUsername(String username);

    public boolean existsByUsername(String username);

    User findByOauthId(String userId);
}
