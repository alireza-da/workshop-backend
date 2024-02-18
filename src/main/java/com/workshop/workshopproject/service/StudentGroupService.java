package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.StudentGroup;

import java.util.List;

public interface StudentGroupService {
    public List<StudentGroup> findAll();

    public StudentGroup findById(int theId);

    public void save(StudentGroup studentGroup);

    public void deleteById(int theId);
}
