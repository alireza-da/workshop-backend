package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.RegistrationRepository;
import com.workshop.workshopproject.entity.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public List<Registration> findAll() {
        return registrationRepository.findAll();
    }

    @Override
    public Registration findById(int theId) {
        Optional<Registration> result = registrationRepository.findById(theId);

        Registration registration = null;

        if (result.isPresent()) {
            registration = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find registration id - " + theId);
        }

        return registration;
    }

    @Override
    public void save(Registration registration) {
        registrationRepository.save(registration);
    }

    @Override
    public void deleteById(int theId) {
        registrationRepository.deleteById(theId);
    }
}
