package com.antaler.smlv.apis.users.model.db;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Generated;
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
@Table(name = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "id",unique = true)
    private String id;

    @Column(name = "alias")
    private String alias;
    
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "birth_date" )
    private Date birthDate;

    @Column(name = "gender")
    private Character gender;
    
    @Column(name= "password")
    private String password; 

    @Column(name= "verified")
    private boolean verified;

    @Column(name= "two_fa_seed")
    private String  twoFaSeed;
    
    @Column(name="tw_fa_recovery_codes")
    private String twFaRecoveryCodes;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<UserHealth> healths; 


}
