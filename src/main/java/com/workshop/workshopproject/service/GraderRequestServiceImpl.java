package com.workshop.workshopproject.service;


import com.workshop.workshopproject.dao.GraderRequestRepository;
import com.workshop.workshopproject.entity.GraderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraderRequestServiceImpl implements GraderRequestService {
    private GraderRequestRepository graderRequestRepository;

    @Autowired
    public GraderRequestServiceImpl(GraderRequestRepository graderRequestRepository) {
        this.graderRequestRepository = graderRequestRepository;
    }

    @Override
    public void save(GraderRequest graderRequest){
        graderRequestRepository.save(graderRequest);
    }

    @Override
    public void deleteById(int id) {
        graderRequestRepository.deleteById(id);
    }

    @Override
    public GraderRequest findById(int id){
        if (graderRequestRepository.findById(id).isPresent()){
            return graderRequestRepository.findById(id).get();
        }
        else throw new RuntimeException("Not found with ID: " +id);
    }

    @Override
    public List<GraderRequest> findAll(){
        return graderRequestRepository.findAll();
    }
}

