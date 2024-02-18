package com.workshop.workshopproject.rest;


import com.workshop.workshopproject.entity.Accountant;
import com.workshop.workshopproject.entity.Payment;
import com.workshop.workshopproject.entity.Role;
import com.workshop.workshopproject.entity.User;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.PaymentService;
import com.workshop.workshopproject.service.RegistrationService;
import com.workshop.workshopproject.user.RoleAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

@RestController
@RequestMapping("api/")
public class PaymentRestController {

    private EntityManager entityManager;
    private PaymentService paymentService;
    private RoleAuthentication roleAuthentication;
    private RegistrationService registrationService;
    private QueryManager queryManager;

    @Autowired
    public PaymentRestController(EntityManager entityManager, PaymentService paymentService, RoleAuthentication roleAuthentication, RegistrationService registrationService, QueryManager queryManager) {
        this.entityManager = entityManager;
        this.paymentService = paymentService;
        this.roleAuthentication = roleAuthentication;
        this.registrationService = registrationService;
        this.queryManager = queryManager;
    }

    @Transactional
    @PutMapping("/payments")
    public ResponseEntity update(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader){
        User user = queryManager.getUser((String)jsonHeader.get("authorization"));
        if (user.hasRole(Accountant.class) == null) {
            return new ResponseEntity<>("NOT ALLOWED", HttpStatus.BAD_REQUEST);
        }

        boolean status = (boolean) jsonBody.get("status");
        int payment_id = (int) jsonBody.get("payment_id");
        Payment payment = paymentService.findById(payment_id);
        payment.setStatus(status);
        entityManager.merge(payment);
        return new ResponseEntity<>("Payment Updated", HttpStatus.OK);
    }

    public void delete(@RequestBody Map<String, Object> jsonBody, @RequestHeader Map<String, Object> jsonHeader){
        if (!(roleAuthentication.authStudent(jsonHeader) || roleAuthentication.authAccountant(jsonHeader))){
            return;
        }
        paymentService.deleteById((int) jsonBody.get("payment_id"));
    }
}
