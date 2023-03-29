package com.example.microservice.coustmerbankdetails.coustmerdeatils;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.net.URI;

@Data
@Component
public class Coustmerdetailresponses {

    private int coustmerid;
    private String firstname;
    private String lastname;

   }
