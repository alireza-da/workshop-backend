package com.workshop.workshopproject.rest;


import com.workshop.workshopproject.entity.Mail;
import com.workshop.workshopproject.properties.EmailProperties;
import com.workshop.workshopproject.service.MailService;
import com.workshop.workshopproject.user.RoleAuthentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/")
public class MailRestController {

    private MailService mailService;
    private EmailProperties emailProperties;
    private RoleAuthentication roleAuthentication;

    public MailRestController(MailService mailService, EmailProperties emailProperties, RoleAuthentication roleAuthentication) {
        this.mailService = mailService;
        this.emailProperties = emailProperties;
        this.roleAuthentication = roleAuthentication;
    }


	@PostMapping("send_email")
    public void sendMail(@RequestBody Map<String, Object> jsonBody){

        String sender = (String) jsonBody.get("sender");
        String receiver = (String) jsonBody.get("receiver");
        String subject = (String) jsonBody.get("subject");
        String content = (String) jsonBody.get("content");
        String personal = (String) jsonBody.get("personal");

        Mail mail = new Mail();
        mail.setMailFrom(sender);
        mail.setMailTo(receiver);
        mail.setMailSubject(subject);
        mail.setMailContent(content);

        mailService.sendEmail(mail,personal);
    }

    @PutMapping("change_email")
    public void changeMail(@RequestBody Map<String, Object> jsonBody,@RequestHeader Map<String, Object> jsonHeader){
        if (!(roleAuthentication.authAdmin(jsonHeader))){
            return;
        }
        emailProperties.setEmail((String) jsonBody.get("email"));
        emailProperties.setPassword((String) jsonBody.get("password:"));
    }

}
