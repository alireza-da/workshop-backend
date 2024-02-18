package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form,Integer> {
}
