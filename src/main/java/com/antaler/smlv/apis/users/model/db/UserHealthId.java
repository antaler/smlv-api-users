package com.antaler.smlv.apis.users.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserHealthId implements Serializable {


    private String userId;

    private LocalDateTime registerDate;
}
