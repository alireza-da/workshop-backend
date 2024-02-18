package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role findById(int theId);

    void save(Role role);

    void deleteById(int theId);
}
