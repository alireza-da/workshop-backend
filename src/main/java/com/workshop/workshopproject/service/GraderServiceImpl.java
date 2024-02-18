package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.GraderRepository;
import com.workshop.workshopproject.entity.Grader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GraderServiceImpl implements GraderService {

    private GraderRepository graderRepository;
    @Autowired
    public GraderServiceImpl(GraderRepository graderRepository) {
        this.graderRepository = graderRepository;
    }

    @Override
    public List<Grader> findAll() {
        return graderRepository.findAll();
    }

    @Override
    public Grader findById(int theId) {
        Optional<Grader> result = graderRepository.findById(theId);

        Grader accountant = null;

        if (result.isPresent()) {
            accountant = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find grader id - " + theId);
        }

        return accountant;
    }

    @Override
    public void save(Grader accountant) {
        graderRepository.save(accountant);
    }

    @Override
    public void deleteById(int theId) {
        graderRepository.deleteById(theId);
    }
}
