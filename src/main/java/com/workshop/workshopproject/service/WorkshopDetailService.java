package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.WorkshopDetail;

import java.util.List;

public interface WorkshopDetailService {
    List<WorkshopDetail> findAll();

    WorkshopDetail findById(int theId);

    void save(WorkshopDetail workshopDetail);

    void deleteById(int theId);

}
