package com.example.microservice.coustmerbankdetails.controller;

import com.example.microservice.coustmerbankdetails.model.Model;
import com.example.microservice.coustmerbankdetails.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/bankdetails")
public class Controller {

    @Autowired
    private Repository repo;
    @Autowired
    private ServiceInstance serviceInstance;
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
}
