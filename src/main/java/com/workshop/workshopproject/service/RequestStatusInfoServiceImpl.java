package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.RegistrationRepository;
import com.workshop.workshopproject.dao.RequestStatusInfoRepository;
import com.workshop.workshopproject.entity.RequestStatusInfo;
import com.workshop.workshopproject.enums.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestStatusInfoServiceImpl implements RequestStatusInfoService {
    private RequestStatusInfoRepository requestStatusInfoRepository;

    @Autowired
    public RequestStatusInfoServiceImpl(RequestStatusInfoRepository requestStatusInfoRepository) {
        this.requestStatusInfoRepository = requestStatusInfoRepository;
    }
    @Override
    public List<RequestStatusInfo> findAll() {
        return requestStatusInfoRepository.findAll();
    }

    @Override
    public RequestStatusInfo findById(int theId) {
        Optional<RequestStatusInfo> result = requestStatusInfoRepository.findById(theId);

        RequestStatusInfo requestStatusInfo = null;

        if (result.isPresent()) {
            requestStatusInfo = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find request status info id - " + theId);
        }

        return requestStatusInfo;
    }

    @Override
    public void save(RequestStatusInfo registration) {
        requestStatusInfoRepository.save(registration);
    }

    @Override
    public void deleteById(int theId) {
        requestStatusInfoRepository.deleteById(theId);
    }
}
