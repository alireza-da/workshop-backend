package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.workshop.workshopproject.enums.WorkshopRelation;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workshop_detail")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@DynamicUpdate
public class WorkshopDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "workshop_relation")
    private WorkshopRelation workshopRelation;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "related_workshop_id")
    private Workshop relatedWorkshop;

    //    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
//    @JoinColumn(name = "workshop_detail_id")
//    private List<Workshop> workshops;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "workshop_id")
    private Workshop workshop;

    public WorkshopDetail() {

    }

    public WorkshopDetail(WorkshopRelation workshopRelation, Workshop relatedWorkshop, Workshop workshop) {
        this.workshopRelation = workshopRelation;
        this.relatedWorkshop = relatedWorkshop;
        this.workshop = workshop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WorkshopRelation getWorkshopRelation() {
        return workshopRelation;
    }

    public void setWorkshopRelation(WorkshopRelation workshopRelation) {
        this.workshopRelation = workshopRelation;
    }

    public Workshop getRelatedWorkshop() {
        return relatedWorkshop;
    }

    public void setRelatedWorkshop(Workshop relatedWorkshop) {
        this.relatedWorkshop = relatedWorkshop;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    //    public List<Workshop> getWorkshops() {
//        return workshops;
//    }
//
//    public void setWorkshops(List<Workshop> workshops) {
//        this.workshops = workshops;
//    }
}