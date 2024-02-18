package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.PaymentRepository;
import com.workshop.workshopproject.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findById(int theId) {
        Optional<Payment> result = paymentRepository.findById(theId);

        Payment payment = null;

        if (result.isPresent()) {
            payment = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find payment id - " + theId);
        }

        return payment;
    }

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public void deleteById(int theId) {
        paymentRepository.deleteById(theId);
    }
}
