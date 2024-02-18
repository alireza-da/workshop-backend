package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.EnrolledWorkshopRepository;
import com.workshop.workshopproject.entity.EnrolledWorkshop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrolledWorkshopServiceImpl implements EnrolledWorkshopService {
    private EnrolledWorkshopRepository enrolledWorkshopRepository;

    @Autowired
    public EnrolledWorkshopServiceImpl(EnrolledWorkshopRepository enrolledWorkshopRepository) {
        this.enrolledWorkshopRepository = enrolledWorkshopRepository;
    }

    @Override
    public List<EnrolledWorkshop> findAll() {
        return enrolledWorkshopRepository.findAll();
    }

    @Override
    public EnrolledWorkshop findById(int theId) {
        Optional<EnrolledWorkshop> result = enrolledWorkshopRepository.findById(theId);

        EnrolledWorkshop enrolledWorkshop = null;

        if (result.isPresent()) {
            enrolledWorkshop = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find workshop id - " + theId);
        }

        return enrolledWorkshop;
    }

    @Override
    public void save(EnrolledWorkshop enrolledWorkshop) {
        enrolledWorkshopRepository.save(enrolledWorkshop);
    }

    @Override
    public void deleteById(int theId) {
        enrolledWorkshopRepository.deleteById(theId);
    }
}
