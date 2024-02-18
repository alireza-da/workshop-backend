package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.WorkshopDetailRepository;
import com.workshop.workshopproject.entity.WorkshopDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshopDetailServiceImpl implements WorkshopDetailService {

    private WorkshopDetailRepository workshopDetailRepository;

    @Autowired
    public WorkshopDetailServiceImpl(WorkshopDetailRepository workshopDetailRepository) {
        this.workshopDetailRepository = workshopDetailRepository;
    }

    @Override
    public List<WorkshopDetail> findAll() {
        return workshopDetailRepository.findAll();
    }

    @Override
    public WorkshopDetail findById(int theId) {
        Optional<WorkshopDetail> result = workshopDetailRepository.findById(theId);

        WorkshopDetail workshopDetail = null;

        if (result.isPresent()) {
            workshopDetail = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find workshop detail id - " + theId);
        }

        return workshopDetail;
    }

    @Override
    public void save(WorkshopDetail workshopDetail) {
        workshopDetailRepository.save(workshopDetail);

    }

    @Override
    public void deleteById(int theId) {
        workshopDetailRepository.deleteById(theId);
    }
}
