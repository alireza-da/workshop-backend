package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enrolled_workshop")
@DynamicUpdate
public class EnrolledWorkshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private int id;

    @JsonBackReference(value = "enrolled-reg")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "registration_id")
    private Registration registration;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "enrolled_workshop_id")
    private List<FilledEvaluationForm> studentEvaluationForms;

    @JsonBackReference(value = "enrolled-group")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "student_group_id")
    private StudentGroup studentGroup;

    public EnrolledWorkshop() {

    }

    public EnrolledWorkshop(Registration registration, List<FilledEvaluationForm> studentEvaluationForms, StudentGroup studentGroup) {
        this.registration = registration;
        this.studentEvaluationForms = studentEvaluationForms;
        this.studentGroup = studentGroup;
    }

    //    public EnrolledWorkshop(int groupId, Registration registration, List<StudentEvaluationForm> studentEvaluationForms) {
//        this.groupId = groupId;
//        this.registration = registration;
//        this.studentEvaluationForms = studentEvaluationForms;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

//    public List<StudentEvaluationForm> getStudentEvaluationForms() {
//        return studentEvaluationForms;
//    }
//
//    public void setStudentEvaluationForms(List<StudentEvaluationForm> studentEvaluationForms) {
//        this.studentEvaluationForms = studentEvaluationForms;
//    }


    public List<FilledEvaluationForm> getStudentEvaluationForms() {
        return studentEvaluationForms;
    }

    public void setStudentEvaluationForms(List<FilledEvaluationForm> studentEvaluationForms) {
        this.studentEvaluationForms = studentEvaluationForms;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }
}
