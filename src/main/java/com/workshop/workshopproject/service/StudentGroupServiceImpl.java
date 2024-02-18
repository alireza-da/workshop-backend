package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.StudentGroupRepository;
import com.workshop.workshopproject.entity.StudentGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentGroupServiceImpl implements StudentGroupService {
    private StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    @Override
    public List<StudentGroup> findAll() {
        return studentGroupRepository.findAll();
    }

    @Override
    public StudentGroup findById(int theId) {
        Optional<StudentGroup> result = studentGroupRepository.findById(theId);

        StudentGroup studentGroup = null;

        if (result.isPresent()) {
            studentGroup = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find student group id - " + theId);
        }

        return studentGroup;
    }

    @Override
    public void save(StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);
    }

    @Override
    public void deleteById(int theId) {
        studentGroupRepository.deleteById(theId);
    }
}
