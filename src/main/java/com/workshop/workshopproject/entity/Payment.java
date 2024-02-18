package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

@Entity
@Table(name = "payment")
@DynamicUpdate
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "amount")
    @NotNull(message = "This field can not be left empty")
    private BigDecimal amount;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "date")
    @NotNull(message = "This field can not be left empty")
    private Calendar date;

    @Column(name = "status")
    @NotNull(message = "This field can not be left empty")
    private Boolean status;

    @JsonBackReference("reg-pay")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "registration_id")
    private Registration registration;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "accountant_id")
    private Accountant accountant;

    public Payment() {
    }

    public Payment(@NotNull(message = "This field can not be left empty") BigDecimal amount, @NotNull(message = "This field can not be left empty") Calendar date, @NotNull(message = "This field can not be left empty") Boolean status, Registration registration, Accountant accountant) {
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.registration = registration;
        this.accountant = accountant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Accountant getAccountant() {
        return accountant;
    }

    public void setAccountant(Accountant accountant) {
        this.accountant = accountant;
    }
}
