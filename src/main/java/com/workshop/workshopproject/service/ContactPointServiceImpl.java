package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.ContactPointRepository;
import com.workshop.workshopproject.dao.UserRepository;
import com.workshop.workshopproject.entity.ContactPoint;
import com.workshop.workshopproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactPointServiceImpl implements ContactPointService {
    private ContactPointRepository contactPointRepository;

    @Autowired
    public ContactPointServiceImpl(ContactPointRepository contactPointRepository) {
        this.contactPointRepository = contactPointRepository;
    }

    @Override
    public List<ContactPoint> findAll() {
        return contactPointRepository.findAll();
    }

    @Override
    public ContactPoint findById(int theId) {
        Optional<ContactPoint> result = contactPointRepository.findById(theId);

        ContactPoint theContactPoint = null;

        if (result.isPresent()) {
            theContactPoint = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find user id - " + theId);
        }

        return theContactPoint;
    }

    @Override
    public void save(ContactPoint theContactPoint) {
        contactPointRepository.save(theContactPoint);
    }

    @Override
    public void deleteById(int theId) {
        contactPointRepository.deleteById(theId);
    }
}
