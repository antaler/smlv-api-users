package com.antaler.smlv.apis.users.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsersControllerTest {



    @Test
    void helloTest(){
        var response = new UsersController().getHello();
        assertEquals("Hi !", response);
    }


}
