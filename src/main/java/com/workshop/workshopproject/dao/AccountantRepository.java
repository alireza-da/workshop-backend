package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.Accountant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountantRepository extends JpaRepository<Accountant, Integer> {

}
