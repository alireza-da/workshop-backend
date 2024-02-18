package com.workshop.workshopproject.rest;

import com.workshop.workshopproject.entity.RequestStatusInfo;
import com.workshop.workshopproject.enums.RequestStatus;
import com.workshop.workshopproject.service.RequestStatusInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class RequestStatusInfoRestController {
    private RequestStatusInfoServiceImpl requestStatusInfoService;

    @Autowired
    public RequestStatusInfoRestController(RequestStatusInfoServiceImpl requestStatusInfoService) {
        this.requestStatusInfoService = requestStatusInfoService;
    }

    // expose "/employees" and return list of employees
    @CrossOrigin(origins = "*")
    @GetMapping("/request_status_infos")
    public List<RequestStatusInfo> findAll() {
        return requestStatusInfoService.findAll();
    }

    // add mapping for GET /employees/{employeeId}

    @RequestMapping(value = "/request_status_infos/{request_status_infos_id}", method = RequestMethod.GET)
    @GetMapping("/request_status_infos/{request_status_infos_id")
    public RequestStatusInfo getContactPoint(@PathVariable int request_status_infos_id) throws RuntimeException {
        try {
            RequestStatusInfo contactPoint = requestStatusInfoService.findById(request_status_infos_id);

            if (contactPoint == null) {
                throw new CustomException("Contact Point id not found - " + request_status_infos_id);
            }

            return contactPoint;
        } catch (Exception exc) {
            throw new RuntimeException();
        }


    }

    // add mapping for POST /employees - add new employee

    @PostMapping("/request_status_infos")
    public RequestStatusInfo addContactPoint(@RequestBody(required = false) RequestStatusInfo contactPoint) {

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        contactPoint.setId(0);

        requestStatusInfoService.save(contactPoint);

        return contactPoint;
    }

    // add mapping for PUT /employees - update existing employee

    @PutMapping("/request_status_infos")
    public RequestStatusInfo updateContactPoint(@RequestBody RequestStatusInfo contactPoint) {

        requestStatusInfoService.save(contactPoint);

        return contactPoint;
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee

    @DeleteMapping("/request_status_infos/{request_status_infos_id}")
    public String deleteContactPoint(@PathVariable int request_status_infos_id) {

        RequestStatusInfo contactPoint = requestStatusInfoService.findById(request_status_infos_id);

        // throw exception if null

        if (contactPoint == null) {
            throw new RuntimeException("Contact Point id not found - " + request_status_infos_id);
        }

        requestStatusInfoService.deleteById(request_status_infos_id);

        return "Deleted Contact Point id - " + request_status_infos_id;
    }
}
