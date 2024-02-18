package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.WorkshopFormRelation;

import java.util.List;

public interface WorkshopFormRelationService {
    public List<WorkshopFormRelation> findAll();

    public void save(WorkshopFormRelation workshopFormRelation);

    public void deleteById(int id);

    public WorkshopFormRelation findById(int id);
}
