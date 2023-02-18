package com.example.microservice.coustmerbankdetails.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="coustomerbankdetails")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="coustmerid")
    private Long coustmerid;
    @Column(name="accountnumber")
    private Long accountnumber;
    @Column(name="branchname")
    private String branchname;
    @Column(name="balance")
    private Long balance;
}
