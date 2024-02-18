package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "answer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DynamicUpdate
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private int id;

    @Column(name = "data")
    private String data;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "filled_eval_id")
    private FilledEvaluationForm filledEvaluationForm;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer() {
    }

    public Answer(String data, FilledEvaluationForm filledEvaluationForm, Question question) {
        this.data = data;
        this.filledEvaluationForm = filledEvaluationForm;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public FilledEvaluationForm getFilledEvaluationForm() {
        return filledEvaluationForm;
    }

    public void setFilledEvaluationForm(FilledEvaluationForm filledEvaluationForm) {
        this.filledEvaluationForm = filledEvaluationForm;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
