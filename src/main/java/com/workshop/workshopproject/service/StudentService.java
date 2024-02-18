package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.Student;

import java.util.List;

public interface StudentService {
    public List<Student> findAll();

    public Student findById(int theId);

    public void save(Student student);

    public void deleteById(int theId);
}
