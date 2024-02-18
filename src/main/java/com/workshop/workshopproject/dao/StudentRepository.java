package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
