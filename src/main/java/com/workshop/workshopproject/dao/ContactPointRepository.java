package com.workshop.workshopproject.dao;

import com.workshop.workshopproject.entity.ContactPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

public interface ContactPointRepository extends JpaRepository<ContactPoint, Integer> {

}
