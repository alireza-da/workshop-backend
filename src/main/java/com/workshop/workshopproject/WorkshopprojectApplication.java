package com.workshop.workshopproject;

import com.workshop.workshopproject.entity.Mail;
import com.workshop.workshopproject.properties.FileStorageProperties;
import com.workshop.workshopproject.service.MailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@EnableJpaRepositories("com.workshop.workshopproject.*")
//@ComponentScan(basePackages = { "com.workshop.workshopproject.*" })
//@EntityScan("com.workshop.workshopproject.*")

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class WorkshopprojectApplication {

	public static void main(String[] args) { SpringApplication.run(WorkshopprojectApplication.class, args); }

}
