package com.workshop.workshopproject.service;

import com.workshop.workshopproject.entity.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> findAll();

    Payment findById(int theId);

    void save(Payment payment);

    void deleteById(int theId);
}
