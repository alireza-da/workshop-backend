package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DynamicUpdate
public class FilledEvaluationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private int id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "filledEvaluationForm")
    private List<Answer> answers;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "form_id")
    private Form form;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "grader_relation_id")
    private GraderWorkshopRelation graderWorkshopRelation;

    public FilledEvaluationForm() {

    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "offered_workshop_id")
    private OfferedWorkshop offeredWorkshop;

    public FilledEvaluationForm(List<Answer> answers, Form form, GraderWorkshopRelation graderWorkshopRelation, OfferedWorkshop offeredWorkshop) {
        this.answers = answers;
        this.form = form;
        this.graderWorkshopRelation = graderWorkshopRelation;
        this.offeredWorkshop = offeredWorkshop;
    }

    public FilledEvaluationForm(List<Answer> answers) {
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public GraderWorkshopRelation getGraderWorkshopRelation() {
        return graderWorkshopRelation;
    }

    public void setGraderWorkshopRelation(GraderWorkshopRelation graderWorkshopRelation) {
        this.graderWorkshopRelation = graderWorkshopRelation;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public OfferedWorkshop getOfferedWorkshop() {
        return offeredWorkshop;
    }

    public void setOfferedWorkshop(OfferedWorkshop offeredWorkshop) {
        this.offeredWorkshop = offeredWorkshop;
    }
}
