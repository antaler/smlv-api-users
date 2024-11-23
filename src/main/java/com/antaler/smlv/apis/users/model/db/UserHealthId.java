package com.antaler.smlv.apis.users.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserHealthId implements Serializable {

    @Column(name = "USER_ID")
    private String userId;
    
    @Column(name = "REGISTER_DATE")
    private LocalDateTime registerDate;
}
