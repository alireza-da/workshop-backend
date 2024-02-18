package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.RequestStatusInfo;

import java.util.List;

public interface RequestStatusInfoService {
    public List<RequestStatusInfo> findAll();

    public RequestStatusInfo findById(int theId);

    public void save(RequestStatusInfo theEmployee);

    public void deleteById(int theId);
}
