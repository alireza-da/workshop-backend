package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.AccountantRepository;
import com.workshop.workshopproject.entity.Accountant;
import com.workshop.workshopproject.entity.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountantServiceImpl implements AccountantService {
    private AccountantRepository accountantRepository;
    @Autowired
    public AccountantServiceImpl(AccountantRepository contactPointRepository) {
        this.accountantRepository = contactPointRepository;
    }

    @Override
    public List<Accountant> findAll() {
        return accountantRepository.findAll();
    }

    @Override
    public Accountant findById(int theId) {
        Optional<Accountant> result = accountantRepository.findById(theId);

        Accountant accountant = null;

        if (result.isPresent()) {
            accountant = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find accountant id - " + theId);
        }

        return accountant;
    }

    @Override
    public void save(Accountant accountant) {
        accountantRepository.save(accountant);
    }

    @Override
    public void deleteById(int theId) {
        accountantRepository.deleteById(theId);
    }
}
