package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.AnswerRepository;
import com.workshop.workshopproject.entity.Answer;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    private AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }


    @Override
    public void save(Answer answer) {
      answerRepository.save(answer);
    }

    @Override
    public void deleteById(int id) {
        answerRepository.deleteById(id);
    }

    @Override
    public void update(Answer answer) {
        answerRepository.save(answer);
    }

    @Override
    public Answer findById(int id) {
        if (answerRepository.findById(id).isPresent()){
            return answerRepository.findById(id).get();
        }
        else {
            throw new RuntimeException("Not find with ID: " + id);
        }
    }

    @Override
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }
}
