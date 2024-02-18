package com.workshop.workshopproject.rest;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.workshop.workshopproject.config.JwtTokenUtil;
import com.workshop.workshopproject.entity.ContactPoint;
import com.workshop.workshopproject.entity.Mail;
import com.workshop.workshopproject.entity.Role;
import com.workshop.workshopproject.entity.User;
import com.workshop.workshopproject.enums.ContactType;
import com.workshop.workshopproject.properties.EmailProperties;
import com.workshop.workshopproject.service.ContactPointService;
import com.workshop.workshopproject.service.MailService;
import com.workshop.workshopproject.service.UserService;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;


@RestController
@RequestMapping("api/")
@CrossOrigin("*")
public class OauthRestController {

    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;
    private ContactPointService contactPointService;
    private MailService mailService;
    private EmailProperties emailProperties;

    public OauthRestController(JwtTokenUtil jwtTokenUtil, UserService userService, ContactPointService contactPointService, MailService mailService, EmailProperties emailProperties) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.contactPointService = contactPointService;
        this.mailService = mailService;
        this.emailProperties = emailProperties;
    }

    @PostMapping(value = "google_auth", headers = "Accept=application/json", consumes = "application/json", produces = "application/json")
    public String generateToken(@RequestBody String jsonBody) throws GeneralSecurityException, IOException {
        String token = jsonBody.substring(8);
        //System.out.println(token);
        com.google.api.client.json.JsonFactory jsonFactory = new JacksonFactory();

        String CLIENT_ID = "884551167510-j2o6egbqu5lps8d4ihkb25dh15tn7qf0.apps.googleusercontent.com";
        HttpTransport transport = new NetHttpTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
        //System.out.println("dsfsdhjf");

        // (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(token);
        System.out.println(idToken);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            //System.out.println("dsfds");
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("u:"+userService.findByOauthId(userId));
            if (userService.findByOauthId(userId) == null){
                System.out.println("pipi");
                return "Register First";
            }

            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            Map<String, Object> claims = new HashMap<>();
            System.out.println(name);
            return jwtTokenUtil.doGenerateToken(claims, name);
            // Use or store profile information
            // ...

        } else {
            System.out.println("Invalid ID token.");
            return null;
        }

    }


    @PostMapping("google_sign_up")
    public User signUp(@RequestBody String jsonBody) throws GeneralSecurityException, IOException {
        com.google.api.client.json.JsonFactory jsonFactory = new JacksonFactory();

        String CLIENT_ID = "884551167510-j2o6egbqu5lps8d4ihkb25dh15tn7qf0.apps.googleusercontent.com";
        HttpTransport transport = new NetHttpTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

// (Receive idTokenString by HTTPS POST)
        String token = jsonBody.substring(8);
        GoogleIdToken idToken = verifier.verify(token);
        System.out.println("id_token"+idToken);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            // Print user identifier
            String userId = payload.getSubject();
            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            User user = new User();
            user.setUsername(name);
            user.setFirstName(givenName);
            user.setLastName(familyName);
            user.setImageUrl(pictureUrl);
            user.setOauthId(userId);
            List<ContactPoint> contactPoints = new ArrayList<>();
            ContactPoint contactPoint = new ContactPoint();

            contactPoint.setContactData(email);
            contactPoint.setContactType(ContactType.Email);
            List<Role> roles = new ArrayList<>();
            user.setRoles(roles);
            user.setPassword(userId);
            contactPoints.add(contactPoint);
            contactPointService.save(contactPoint);
            user.setContactPoints(contactPoints);

            userService.save(user);
            contactPoint.setUser(user);
            Mail mail = new Mail();
            mail.setMailContent("Welcome to our institute\n Username:"+ user.getUsername() + "\nEmail:"+email);
            mail.setMailFrom(emailProperties.getEmail());
            mail.setMailSubject("Sign Up successful");
            mail.setMailTo(email);
            mailService.sendEmail(mail,emailProperties.getEmail());
            return user;
        }

        else {

            return null;
        }
    }
}
