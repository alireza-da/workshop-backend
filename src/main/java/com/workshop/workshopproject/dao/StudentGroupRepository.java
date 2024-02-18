package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Integer> {

}
