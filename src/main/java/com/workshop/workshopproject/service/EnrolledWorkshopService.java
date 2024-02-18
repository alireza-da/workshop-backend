package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.EnrolledWorkshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrolledWorkshopService {
    List<EnrolledWorkshop> findAll();

    EnrolledWorkshop findById(int theId);

    void save(EnrolledWorkshop enrolledWorkshop);

    void deleteById(int theId);
}
