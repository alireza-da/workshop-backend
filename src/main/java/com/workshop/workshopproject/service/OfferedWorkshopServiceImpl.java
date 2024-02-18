package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.OfferedWorkshopRepository;
import com.workshop.workshopproject.dao.WorkshopRepository;
import com.workshop.workshopproject.entity.OfferedWorkshop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferedWorkshopServiceImpl implements OfferedWorkshopService {
    private OfferedWorkshopRepository offeredWorkshopRepository;

    @Autowired
    public OfferedWorkshopServiceImpl(OfferedWorkshopRepository offeredWorkshopRepository) {
        this.offeredWorkshopRepository = offeredWorkshopRepository;
    }

    @Override
    public List<OfferedWorkshop> findAll() {
        return offeredWorkshopRepository.findAll();
    }

    @Override
    public OfferedWorkshop findById(int theId) {
        Optional<OfferedWorkshop> result = offeredWorkshopRepository.findById(theId);

        OfferedWorkshop offeredWorkshop = null;

        if (result.isPresent()) {
            offeredWorkshop = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find workshop id - " + theId);
        }

        return offeredWorkshop;
    }

    @Override
    public void save(OfferedWorkshop offeredWorkshop) {
        offeredWorkshopRepository.save(offeredWorkshop);
    }

    @Override
    public void deleteById(int theId) {
        offeredWorkshopRepository.deleteById(theId);
    }

//    @Override
//    public boolean existsByTitle(String title) { }
}
