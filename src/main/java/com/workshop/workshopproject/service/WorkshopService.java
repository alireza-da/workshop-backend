package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.Workshop;

import java.util.List;

public interface WorkshopService {
    List<Workshop> findAll();

    Workshop findById(int theId);

    void save(Workshop workshop);

    void deleteById(int theId);

    boolean existsByTitle(String title);
}
