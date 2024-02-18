package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.FilledEvaluationForm;

import java.util.List;

public interface FilledEvaluationFormService {
    public void save(FilledEvaluationForm filledEvaluationForm);
    public void deleteById(int id);
    public FilledEvaluationForm findById(int id);
    public List<FilledEvaluationForm> findAll();
}
