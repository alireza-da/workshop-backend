package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.GraderRequest;

import java.util.List;

public interface GraderRequestService {
    public List<GraderRequest> findAll();
    public GraderRequest findById(int id);
    public void save(GraderRequest graderRequest);
    public void deleteById(int id);
}
