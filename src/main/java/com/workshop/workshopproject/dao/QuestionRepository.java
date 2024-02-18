package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Integer> {

}
