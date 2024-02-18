package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.WorkshopFormRelationRepository;
import com.workshop.workshopproject.entity.WorkshopFormRelation;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WorkshopFormRelationServiceImpl implements WorkshopFormRelationService {
    private WorkshopFormRelationRepository workshopFormRelationRepository;

    public WorkshopFormRelationServiceImpl(WorkshopFormRelationRepository workshopFormRelationRepository) {
        this.workshopFormRelationRepository = workshopFormRelationRepository;
    }

    @Override
    public List<WorkshopFormRelation> findAll() {
        return workshopFormRelationRepository.findAll();
    }

    @Override
    public void save(WorkshopFormRelation workshopFormRelation) {
        workshopFormRelationRepository.save(workshopFormRelation);
    }

    @Override
    public void deleteById(int id) {
        workshopFormRelationRepository.deleteById(id);
    }

    @Override
    public WorkshopFormRelation findById(int id) {
        if (workshopFormRelationRepository.findById(id).isPresent()){
            return workshopFormRelationRepository.findById(id).get();

        }
        else throw new RuntimeException("Not found with id: " + id);
    }
}
