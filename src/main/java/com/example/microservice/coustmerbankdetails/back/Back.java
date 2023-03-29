package com.example.microservice.coustmerbankdetails.back;

import com.example.microservice.coustmerbankdetails.responses.Responses;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Back {
    private Responses responses;
}
