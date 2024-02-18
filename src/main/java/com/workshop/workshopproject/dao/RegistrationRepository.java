package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

}
