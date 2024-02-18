package com.workshop.workshopproject.service;


import com.workshop.workshopproject.entity.GraderWorkshopRelation;

public interface GraderWorkshopRelationService {
    public void save(GraderWorkshopRelation graderWorkshopRelation);
    public void deleteById(int id);
    public GraderWorkshopRelation findById(int id);
}
