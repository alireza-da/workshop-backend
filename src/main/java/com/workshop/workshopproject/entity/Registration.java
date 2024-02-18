package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.*;
import com.workshop.workshopproject.utils.DateHandler;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "registration")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DynamicUpdate
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonManagedReference(value = "enrolled-reg")
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "registration")
    private EnrolledWorkshop enrolledWorkshop;

    @JsonManagedReference(value = "reg-pay")
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "registration")
    private List<Payment> payments = new ArrayList<>();

//    @JsonBackReference(value = "student-reg")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "request_status_id")
    private RequestStatusInfo RequestStatusInfo;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "offered_workshop_id")
    private OfferedWorkshop offeredWorkshop;


    public Registration() {

    }



//    public Registration(EnrolledWorkshop enrolledWorkshop, List<Payment> payments, Student student, OfferedWorkshop offeredWorkshop) {
//        this.enrolledWorkshop = enrolledWorkshop;
//        this.payments = payments;
//        this.student = student;
//        this.offeredWorkshop = offeredWorkshop;
//    }
//
//    public Registration(String body, RequestStatusInfo requestStatusInfo, EnrolledWorkshop enrolledWorkshop, List<Payment> payments, Student student, OfferedWorkshop offeredWorkshop) {
//        super(body, requestStatusInfo);
//        this.enrolledWorkshop = enrolledWorkshop;
//        this.payments = payments;
//        this.student = student;
//        this.offeredWorkshop = offeredWorkshop;
//    }


    public Registration(EnrolledWorkshop enrolledWorkshop, List<Payment> payments, Student student, com.workshop.workshopproject.entity.RequestStatusInfo requestStatusInfo, OfferedWorkshop offeredWorkshop) {
        this.enrolledWorkshop = enrolledWorkshop;
        this.payments = payments;
        this.student = student;
        RequestStatusInfo = requestStatusInfo;
        this.offeredWorkshop = offeredWorkshop;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnrolledWorkshop getEnrolledWorkshop() {
        return enrolledWorkshop;
    }

    public void setEnrolledWorkshop(EnrolledWorkshop enrolledWorkshop) {
        this.enrolledWorkshop = enrolledWorkshop;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public OfferedWorkshop getOfferedWorkshop() {
        return offeredWorkshop;
    }

    public void setOfferedWorkshop(OfferedWorkshop offeredWorkshop) {
        this.offeredWorkshop = offeredWorkshop;
    }

    public com.workshop.workshopproject.entity.RequestStatusInfo getRequestStatusInfo() {
        return RequestStatusInfo;
    }

    public void setRequestStatusInfo(com.workshop.workshopproject.entity.RequestStatusInfo requestStatusInfo) {
        RequestStatusInfo = requestStatusInfo;
    }
}
