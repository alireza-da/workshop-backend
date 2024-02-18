package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.FilledEvaluationFormRepository;
import com.workshop.workshopproject.entity.FilledEvaluationForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilledEvaluationFormServiceImpl implements FilledEvaluationFormService {
    private FilledEvaluationFormRepository filledEvaluationFormRepository;

    public FilledEvaluationFormServiceImpl(FilledEvaluationFormRepository filledEvaluationFormRepository) {
        this.filledEvaluationFormRepository = filledEvaluationFormRepository;
    }

    @Override
    public void save(FilledEvaluationForm filledEvaluationForm) {
        filledEvaluationFormRepository.save(filledEvaluationForm);
    }

    @Override
    public void deleteById(int id) {
        filledEvaluationFormRepository.deleteById(id);
    }

    @Override
    public FilledEvaluationForm findById(int id) {
        if (filledEvaluationFormRepository.findById(id).isPresent()){
            return filledEvaluationFormRepository.findById(id).get();
        }
        else throw new RuntimeException("Not found with id: "  +id);
    }

    public List<FilledEvaluationForm> findAll(){
        return filledEvaluationFormRepository.findAll();
    }
}
