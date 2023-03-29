package com.example.microservice.coustmerbankdetails.responses;

import com.example.microservice.coustmerbankdetails.coustmerdeatils.Coustmerdetailresponses;
import com.example.microservice.coustmerbankdetails.model.Model;
import lombok.Data;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Component
public class Responses {  // below variables names should match with another rest api response varible.
    private String name;
    private Coustmerdetailresponses coustmerdetails;
    // private Bankdetailsresponse Bankdetails;
    private Model Bankdetails;
}
 //   private String name; another restapi response varibles and above should match.
   // private Model coustmerdetails;
    //private Bankdetailsresponse Bankdetails;

//important: here we calling another rest api. see we calling rest api response should match with above we place. in order to inject in that.