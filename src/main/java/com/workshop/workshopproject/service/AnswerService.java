package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.Answer;

import java.util.List;

public interface AnswerService {
    public void save(Answer answer);
    public void deleteById(int id);
    public void update(Answer answer);
    public Answer findById(int id);
    public List<Answer> findAll();
}
