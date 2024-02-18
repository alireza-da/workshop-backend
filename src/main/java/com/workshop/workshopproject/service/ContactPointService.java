package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.ContactPoint;

import java.util.List;

public interface ContactPointService {
    public List<ContactPoint> findAll();

    public ContactPoint findById(int theId);

    public void save(ContactPoint theEmployee);

    public void deleteById(int theId);
}
