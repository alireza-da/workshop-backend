package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorRepository extends JpaRepository<Supervisor, Integer> {
}
