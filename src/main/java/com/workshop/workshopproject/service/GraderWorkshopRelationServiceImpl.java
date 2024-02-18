package com.workshop.workshopproject.service;


import com.workshop.workshopproject.dao.GraderWorkshopRelationRepository;
import com.workshop.workshopproject.entity.GraderWorkshopRelation;
import org.springframework.stereotype.Service;


@Service
public class GraderWorkshopRelationServiceImpl implements GraderWorkshopRelationService {
    private GraderWorkshopRelationRepository graderWorkshopRelationRepository;

    @Override
    public void save(GraderWorkshopRelation graderWorkshopRelation) {
        graderWorkshopRelationRepository.save(graderWorkshopRelation);
    }

    @Override
    public void deleteById(int id) {
        graderWorkshopRelationRepository.deleteById(id);
    }

    @Override
    public GraderWorkshopRelation findById(int id) {
        if (graderWorkshopRelationRepository.findById(id).isPresent()){
            return graderWorkshopRelationRepository.findById(id).get();
        }
        else throw new RuntimeException("Not found with ID: " +id);
    }
}
