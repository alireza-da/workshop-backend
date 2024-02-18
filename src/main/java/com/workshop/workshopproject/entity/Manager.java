package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Entity
//@Table(name = "manager")
@DiscriminatorValue(value = "manager")
@DynamicUpdate
public class Manager extends Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int id;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, mappedBy = "manager")
    private List<Workshop> workshops;

    public Manager() {

    }

    public Manager(List<Workshop> workshops) {
        this.workshops = workshops;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }
}
