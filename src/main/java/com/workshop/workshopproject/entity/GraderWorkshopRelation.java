package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.workshop.workshopproject.enums.GraderType;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
public class GraderWorkshopRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "grader_type")
    private GraderType graderType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grader_request_id")
    private GraderRequest graderRequest;

    @JsonBackReference(value = "grader-group")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "student_group_id")
    private StudentGroup studentGroup;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "graderWorkshopRelation")
    private List<FilledEvaluationForm> filledEvaluationForms;

    public GraderWorkshopRelation() {

    }

    public GraderWorkshopRelation(GraderType graderType, GraderRequest graderRequest, StudentGroup studentGroup) {
        this.graderType = graderType;
        this.graderRequest = graderRequest;
        this.studentGroup = studentGroup;
    }

    public GraderWorkshopRelation(GraderType graderType, GraderRequest graderRequest, StudentGroup studentGroup, List<FilledEvaluationForm> filledEvaluationForms) {
        this.graderType = graderType;
        this.graderRequest = graderRequest;
        this.studentGroup = studentGroup;
        this.filledEvaluationForms = filledEvaluationForms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GraderType getGraderType() {
        return graderType;
    }

    public void setGraderType(GraderType graderType) {
        this.graderType = graderType;
    }

    public GraderRequest getGraderRequest() {
        return graderRequest;
    }

    public void setGraderRequest(GraderRequest graderRequest) {
        this.graderRequest = graderRequest;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public List<FilledEvaluationForm> getFilledEvaluationForms() {
        return filledEvaluationForms;
    }

    public void setFilledEvaluationForms(List<FilledEvaluationForm> filledEvaluationForms) {
        this.filledEvaluationForms = filledEvaluationForms;
    }
}
