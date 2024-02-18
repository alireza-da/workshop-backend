package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.Grader;

import java.util.List;

public interface GraderService {
    List<Grader> findAll();

    Grader findById(int theId);

    void save(Grader grader);

    void deleteById(int theId);
}
