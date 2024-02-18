package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.Grader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraderRepository extends JpaRepository<Grader, Integer> {

}
