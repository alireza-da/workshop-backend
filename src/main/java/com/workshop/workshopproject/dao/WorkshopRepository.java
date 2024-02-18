package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository extends JpaRepository<Workshop, Integer> {
    boolean existsByTitle(String title);
}
