package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
