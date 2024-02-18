package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.SupervisorRepository;
import com.workshop.workshopproject.entity.Supervisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupervisorServiceImpl implements SupervisorService {

    private SupervisorRepository supervisorRepository;

    @Autowired
    public SupervisorServiceImpl(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    @Override
    public List<Supervisor> findAll() {
        return supervisorRepository.findAll();
    }

    @Override
    public Supervisor findById(int theId) {
        Optional<Supervisor> result = supervisorRepository.findById(theId);

        Supervisor supervisor = null;

        if (result.isPresent()) {
            supervisor = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find Supervisor id - " + theId);
        }

        return supervisor;
    }

    @Override
    public void save(Supervisor supervisor) {
        supervisorRepository.save(supervisor);
    }

    @Override
    public void deleteById(int theId) {
        supervisorRepository.deleteById(theId);
    }
}
