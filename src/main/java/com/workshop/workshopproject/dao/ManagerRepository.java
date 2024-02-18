package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
}
