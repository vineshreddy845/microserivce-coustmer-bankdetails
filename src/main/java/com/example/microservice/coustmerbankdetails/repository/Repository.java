package com.example.microservice.coustmerbankdetails.repository;

import com.example.microservice.coustmerbankdetails.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Repository extends JpaRepository<Model, Long> {

    //  public Model findBybranchname(String branchname);
    @Query("SELECT m FROM Model m WHERE m.coustmerid = :coustmerid")
   Model  findBycoustmerid(Long coustmerid);
    @Query("SELECT m.coustmerid FROM Model m")
    List<Long> findAllCustomerIds();
}
