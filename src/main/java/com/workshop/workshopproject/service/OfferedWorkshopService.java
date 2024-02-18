package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.OfferedWorkshop;
import java.util.List;

public interface OfferedWorkshopService {
    List<OfferedWorkshop> findAll();

    OfferedWorkshop findById(int theId);

    void save(OfferedWorkshop offeredWorkshop);

    void deleteById(int theId);

//    boolean existsByTitle(String title);
}
