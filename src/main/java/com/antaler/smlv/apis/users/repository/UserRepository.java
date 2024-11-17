package com.antaler.smlv.apis.users.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.antaler.smlv.apis.users.model.db.UserEntity;


public interface UserRepository extends CrudRepository<UserEntity,String>{


    Optional<UserEntity> findFirstByEmail(String email);

}
