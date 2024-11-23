package com.antaler.smlv.apis.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		// System.out.println("SEED 2FA: " + TOTPServiceImpl.encodeBase32(UUID.randomUUID().toString().replace("-", "")).substring(0,32));
		SpringApplication.run(UsersApplication.class, args);
	}

}
