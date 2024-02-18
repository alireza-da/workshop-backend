package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.workshop.workshopproject.enums.FormAbout;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DynamicUpdate
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "form_id")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "form")
    private List<WorkshopFormRelation> workshopFormRelations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "form")
    private List<FilledEvaluationForm> filledEvaluationForms = new ArrayList<>();

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Calendar createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deadline")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Calendar deadline;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "form_about")
//    private FormAbout formAbout;

    public Form() {

    }

    public Form(String title, List<Question> questions, List<WorkshopFormRelation> workshopFormRelations, List<FilledEvaluationForm> filledEvaluationForms, Calendar createdDate, Calendar deadline, FormAbout formAbout) {
        this.title = title;
        this.questions = questions;
        this.workshopFormRelations = workshopFormRelations;
        this.filledEvaluationForms = filledEvaluationForms;
        this.createdDate = createdDate;
        this.deadline = deadline;
//        this.formAbout = formAbout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<WorkshopFormRelation> getWorkshopFormRelations() {
        return workshopFormRelations;
    }

    public void setWorkshopFormRelations(List<WorkshopFormRelation> workshopFormRelations) {
        this.workshopFormRelations = workshopFormRelations;
    }

    public List<FilledEvaluationForm> getFilledEvaluationForms() {
        return filledEvaluationForms;
    }

    public void setFilledEvaluationForms(List<FilledEvaluationForm> filledEvaluationForms) {
        this.filledEvaluationForms = filledEvaluationForms;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }

//    public FormAbout getFormAbout() {
//        return formAbout;
//    }
//
//    public void setFormAbout(FormAbout formAbout) {
//        this.formAbout = formAbout;
//    }
}
