package com.jojoldu.book.freelecspringboot2webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
@SpringBootApplication
public class FreelecSpringboot2WebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreelecSpringboot2WebServiceApplication.class, args);
    }

}
