package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.Supervisor;

import java.util.List;

public interface SupervisorService {
    public List<Supervisor> findAll();

    public Supervisor findById(int theId);

    public void save(Supervisor student);

    public void deleteById(int theId);
}
