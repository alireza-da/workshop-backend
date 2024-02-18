package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.QuestionRepository;
import com.workshop.workshopproject.entity.ContactPoint;
import com.workshop.workshopproject.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;


    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public void deleteById(int id) {
        questionRepository.deleteById(id);
    }

    @Override
    public void update(Question question) {

    }

    @Override
    public Question findById(int theId) {
        Optional<Question> result = questionRepository.findById(theId);

        Question theContactPoint = null;

        if (result.isPresent()) {
            theContactPoint = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find user id - " + theId);
        }

        return theContactPoint;
    }

}
