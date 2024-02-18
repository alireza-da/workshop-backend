package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.OfferedWorkshop;
import com.workshop.workshopproject.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferedWorkshopRepository extends JpaRepository<OfferedWorkshop, Integer> {

}
