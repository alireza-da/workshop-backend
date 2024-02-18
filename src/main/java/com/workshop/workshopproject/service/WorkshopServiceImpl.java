package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.WorkshopRepository;
import com.workshop.workshopproject.entity.Workshop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshopServiceImpl implements WorkshopService {
    private WorkshopRepository workshopRepository;

    @Autowired
    public WorkshopServiceImpl(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Override
    public List<Workshop> findAll() {
        return workshopRepository.findAll();
    }

    @Override
    public Workshop findById(int theId) {
        Optional<Workshop> result = workshopRepository.findById(theId);

        Workshop workshop = null;

        if (result.isPresent()) {
            workshop = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find workshop id - " + theId);
        }

        return workshop;
    }

    @Override
    public void save(Workshop workshop) {
        workshopRepository.save(workshop);

    }

    @Override
    public void deleteById(int theId) {
        workshopRepository.deleteById(theId);
    }

    @Override
    public boolean existsByTitle(String title) {
        return workshopRepository.existsByTitle(title);
    }
}
