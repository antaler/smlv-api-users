package com.antaler.smlv.apis.users.model.db;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_health")
@IdClass(value = UserHealthId.class)
public class UserHealth{

    @Id
    private String userId;
    
    @Id
    private LocalDateTime registerDate;

    @Column(name="height")
    private String height;

    @Column(name= "weight")
    private String weight;


}
