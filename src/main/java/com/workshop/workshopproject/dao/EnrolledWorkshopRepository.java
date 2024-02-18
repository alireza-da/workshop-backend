package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.EnrolledWorkshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolledWorkshopRepository extends JpaRepository<EnrolledWorkshop, Integer> {
}
