package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.Accountant;

import java.util.List;

public interface AccountantService {
    List<Accountant> findAll();

    Accountant findById(int theId);

    void save(Accountant accountant);

    void deleteById(int theId);
}
