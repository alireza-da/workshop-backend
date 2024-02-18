package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.Registration;

import java.util.List;

public interface RegistrationService {
    List<Registration> findAll();

    Registration findById(int theId);

    void save(Registration registration);

    void deleteById(int theId);
}
