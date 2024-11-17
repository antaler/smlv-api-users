package com.antaler.smlv.apis.users.model.db;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "SMLV_HEALTH")
@IdClass(value = UserHealthId.class)
public class UserHealth{

    @Id
    private String userId;
    
    @Id
    private LocalDateTime registerDate;

    @Column(name="HEIGHT")
    private String height;

    @Column(name= "WEIGHT")
    private String weight;


}
