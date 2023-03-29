package com.example.microservice.coustmerbankdetails.controller;

import com.example.microservice.coustmerbankdetails.back.Back;
import com.example.microservice.coustmerbankdetails.coustmerdeatils.Coustmerdetailresponses;
import com.example.microservice.coustmerbankdetails.model.Model;
import com.example.microservice.coustmerbankdetails.repository.Repository;
import com.example.microservice.coustmerbankdetails.responses.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bankdetails")
public class Controller {

    @Autowired
    private Repository repo;
    @Autowired
    private ServiceInstance serviceInstance;
    @Autowired
    private Responses responses;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Coustmerdetailresponses coustmerdetailresponses;
    @Autowired
    private Back back;

    //http://localhost:8081/bankdetails/save
    @PostMapping("/save")
    public Model save(@RequestBody Model model){

       return repo.save(model);
    }
     @PutMapping("/findbyid/{id}")
    public Model update(@RequestBody Model mode, @PathVariable(value="id") Long id){
       //Model model1 = repo.findBybranchname(branchname).get();
       Model model1 = repo.findById(id).get();
        model1.setCoustmerid(mode.getCoustmerid());
        return repo.save(model1);
    }
   // calling from another microserivce. name is microserivcecoustmerdetails
    @GetMapping("/checking/{coustmerid}")
    public Model response(@PathVariable(value="coustmerid") Long coustmerid){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Calling from coustmer-details service coustmerid: {}", coustmerid);
        logger.debug("Calling from coustomer-details service coustmerid: {}", coustmerid);
        URI uri =serviceInstance.getUri();
        System.out.println(uri);

        return repo.findBycoustmerid(coustmerid);
    }

    // we are calling another restapi.
    // below we hardcoded in restapi with coustmerbankdetails change that.
    // below we are getting response from coustmerdetails. with string name and coustmerdetails and bankdetails.
    // so that response should injected to that varibles with same varible name another restapi have.
    @GetMapping("/circuitbreaker/{coustmerid}/{name}")

    public Back rep(@PathVariable(value="coustmerid") Long coustmerid,@PathVariable(value="name")String name){

     //back.setResponses(repo.findBycoustmerid(coustmerid));
   ServiceInstance serviceInstance1= loadBalancerClient.choose(name);

    URI uri=serviceInstance1.getUri();
   // responses.setCoustmerdetailresponses(restTemplate.getForObject(uri+"/details/checkss/{coustmerid}/{name}", Coustmerdetailresponses.class,coustmerid,name));
       // responses.setServiceInstance(restTemplate.getForObject(uri+"/details/uri",ServiceInstance.class));
      back.setResponses(restTemplate.getForObject(uri+"/details/checkss/{coustmerid}/{name}",Responses.class,coustmerid,"COUSTMERBANKDETAILS"));
     return back;

    }
}
