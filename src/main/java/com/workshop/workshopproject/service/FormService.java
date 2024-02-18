package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.Form;
import com.workshop.workshopproject.entity.Question;

import java.util.List;

public interface FormService {
    public Form findById(int id);
    public List<Form> findAll();
    public void save(Form form);
    public void deleteById(int id);
}
