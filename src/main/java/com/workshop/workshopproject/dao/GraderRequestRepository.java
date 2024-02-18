package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.GraderRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraderRequestRepository extends JpaRepository<GraderRequest, Integer> {
}
