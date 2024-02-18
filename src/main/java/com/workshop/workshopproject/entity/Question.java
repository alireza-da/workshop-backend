package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DynamicUpdate
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private int id;

    @NotNull(message = "This field can not be left empty")
    @NotEmpty(message = "This field can not be left empty")
    @Column(name = "data")
    private String data;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "form_id")
    private Form form;


    public Question() {

    }

    public Question(@NotNull(message = "This field can not be left empty") @NotEmpty(message = "This field can not be left empty") String data, List<Answer> answers, Form form) {
        this.data = data;
        this.answers = answers;
        this.form = form;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
