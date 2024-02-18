package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.RequestStatusInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestStatusInfoRepository extends JpaRepository<RequestStatusInfo, Integer> {
}
