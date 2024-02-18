package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_group")
@DynamicUpdate
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonManagedReference(value = "enrolled-group")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "studentGroup")
    private List<EnrolledWorkshop> enrolledWorkshops = new ArrayList<>();

    @JsonBackReference("workshop-group")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "offered_workshop_id")
    private OfferedWorkshop offeredWorkshop;

    @JsonManagedReference(value = "grader-group")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "studentGroup")
    private List<GraderWorkshopRelation> graderWorkshopRelations = new ArrayList<>();

    public StudentGroup() {

    }

    public StudentGroup(List<EnrolledWorkshop> enrolledWorkshops) {
        this.enrolledWorkshops = enrolledWorkshops;
//        this.graderWorkshopRelations = graderWorkshopRelations;
    }

    public StudentGroup(List<EnrolledWorkshop> enrolledWorkshops, OfferedWorkshop offeredWorkshop) {
        this.enrolledWorkshops = enrolledWorkshops;
        this.offeredWorkshop = offeredWorkshop;
    }

    public StudentGroup(List<EnrolledWorkshop> enrolledWorkshops, OfferedWorkshop offeredWorkshop, List<GraderWorkshopRelation> graderWorkshopRelations) {
        this.enrolledWorkshops = enrolledWorkshops;
        this.offeredWorkshop = offeredWorkshop;
        this.graderWorkshopRelations = graderWorkshopRelations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<EnrolledWorkshop> getEnrolledWorkshops() {
        return enrolledWorkshops;
    }

    public OfferedWorkshop getOfferedWorkshop() {
        return offeredWorkshop;
    }

    public void setOfferedWorkshop(OfferedWorkshop offeredWorkshop) {
        this.offeredWorkshop = offeredWorkshop;
    }

    public void setEnrolledWorkshops(List<EnrolledWorkshop> enrolledWorkshops) {
        this.enrolledWorkshops = enrolledWorkshops;
    }

    public EnrolledWorkshop hasEnrolledWorkshop(int enrolledWorkshopId) {
        for (EnrolledWorkshop enrolledWorkshop : this.enrolledWorkshops) {
            if (enrolledWorkshop.getId() == enrolledWorkshopId) {
                return enrolledWorkshop;
            }
        }
        return null;
    }

    public List<GraderWorkshopRelation> getGraderWorkshopRelations() {
        return graderWorkshopRelations;
    }

    public void setGraderWorkshopRelations(List<GraderWorkshopRelation> graderWorkshopRelations) {
        this.graderWorkshopRelations = graderWorkshopRelations;
    }
}
