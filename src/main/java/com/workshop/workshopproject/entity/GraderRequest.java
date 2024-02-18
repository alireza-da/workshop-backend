package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "grader_request")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DynamicUpdate
public class GraderRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

//    @JsonBackReference(value = "grader-req")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "grader_id")
    private Grader grader;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "offered_workshop_id")
    private OfferedWorkshop offeredWorkshop;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "graderRequest")
    private GraderWorkshopRelation graderWorkshopRelation;

    @Column(name = "body")
    private String body;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "request_status_id")
    private RequestStatusInfo requestStatusInfo;

    public GraderRequest() {

    }

    public GraderRequest(Grader grader, OfferedWorkshop offeredWorkshop, String body, com.workshop.workshopproject.entity.RequestStatusInfo requestStatusInfo, GraderWorkshopRelation graderWorkshopRelation) {
        this.grader = grader;
        this.offeredWorkshop = offeredWorkshop;
        this.body = body;
        this.requestStatusInfo = requestStatusInfo;
        this.graderWorkshopRelation = graderWorkshopRelation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Grader getGrader() {
        return grader;
    }

    public void setGrader(Grader grader) {
        this.grader = grader;
    }

    public OfferedWorkshop getOfferedWorkshop() {
        return offeredWorkshop;
    }

    public void setOfferedWorkshop(OfferedWorkshop offeredWorkshop) {
        this.offeredWorkshop = offeredWorkshop;
    }

    public RequestStatusInfo getRequestStatusInfo() {
        return requestStatusInfo;
    }

    public void setRequestStatusInfo(RequestStatusInfo requestStatusInfo) {
        this.requestStatusInfo = requestStatusInfo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public GraderWorkshopRelation getGraderWorkshopRelation() {
        return graderWorkshopRelation;
    }

    public void setGraderWorkshopRelation(GraderWorkshopRelation graderWorkshopRelation) {
        this.graderWorkshopRelation = graderWorkshopRelation;
    }
}
