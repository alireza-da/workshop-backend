package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
public interface UserRepository extends JpaRepository<User, String> {
    public boolean existsByUsername(String username);
    public User findByUsername(String userName);
}
