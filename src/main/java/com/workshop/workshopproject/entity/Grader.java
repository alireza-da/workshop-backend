package com.workshop.workshopproject.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "grader")
@DynamicUpdate
//@DiscriminatorValue(value = "grader")
public class Grader extends Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JoinColumn(name = "id")
//    private int id;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "grader")
    private List<GraderRequest> graderRequests = new ArrayList<>();

    public Grader() {

    }

    public Grader(List<GraderRequest> graderRequests) {
        this.graderRequests = graderRequests;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public List<GraderRequest> getGraderRequests() {
        return graderRequests;
    }

    public void setGraderRequests(List<GraderRequest> graderRequests) {
        this.graderRequests = graderRequests;
    }
}
