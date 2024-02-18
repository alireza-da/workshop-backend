package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "accountant")
@DiscriminatorValue(value = "accountant")
@DynamicUpdate
public class Accountant extends Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JoinColumn(name = "id")
//    private int id;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "accountant_id")
    private List<Payment> payments = new ArrayList<>();

    public Accountant() {

    }

    public Accountant(List<Payment> payments) {
        this.payments = payments;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
