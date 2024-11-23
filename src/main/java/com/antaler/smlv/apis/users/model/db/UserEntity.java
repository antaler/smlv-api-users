package com.antaler.smlv.apis.users.model.db;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SMLV_USERS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "ID",unique = true)
    private String id;

    @Column(name = "ALIAS")
    private String alias;
    
    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @Column(name = "GENDER")
    private Character gender;
    
    @Column(name= "PASSWORD")
    private String password; 

    @Column(name= "INVITATIONS")
    private Integer  invitations;

    @Column(name= "TWO_FA_SEED")
    private String  twoFaSeed;
    
    @Column(name="TW_FA_RECOVERY_CODES")
    private String twFaRecoveryCodes;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<UserHealth> healths; 


}
