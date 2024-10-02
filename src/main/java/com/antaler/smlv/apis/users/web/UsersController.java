package com.antaler.smlv.apis.users.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("v1/users")
public class UsersController {

    @GetMapping("hello")
    public String getHello() {
        return "Hi !";
    }
;    

}
