package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "student")
@DiscriminatorValue(value = "student")
@DynamicUpdate
public class Student extends Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JoinColumn(name = "id")
//    private int id;

//    @JsonManagedReference(value = "student-reg")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<Registration> registrations = new ArrayList<>();

    public Student() {

    }

    public Student(List<Registration> registrations) {
        this.registrations = registrations;
    }


    //    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }

}
