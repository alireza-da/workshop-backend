package com.workshop.workshopproject.service;


import com.workshop.workshopproject.dao.FormRepository;
import com.workshop.workshopproject.entity.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FormServiceImpl implements FormService {
    private FormRepository formRepository;

    @Autowired
    public FormServiceImpl(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    public List<Form> findAll(){
        return formRepository.findAll();
    }

    public Form findById(int id){
        if (formRepository.findById(id).isPresent()){
            return formRepository.findById(id).get();
        }
        else {
            throw new RuntimeException("GraderEvaluationForm with id " + id + " not found");
        }
    }

    public void save(Form form){
        formRepository.save(form);

    }

    public void deleteById(int id){
        formRepository.deleteById(id);
    }
}
