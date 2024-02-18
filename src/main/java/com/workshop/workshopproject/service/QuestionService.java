package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.Question;

public interface QuestionService {

    public void save(Question question);
    public void deleteById(int id);
    public void update(Question question);
    public Question findById(int id);

}
