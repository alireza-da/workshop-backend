package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
}
