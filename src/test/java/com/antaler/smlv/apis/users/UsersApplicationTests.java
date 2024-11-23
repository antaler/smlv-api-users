package com.antaler.smlv.apis.users;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"MAIL_PORT=00000",
		"MAIL_HOST=localhost",
		"MAIL_USER=x",
		"MAIL_PASS=x",
		"jdbc.driverClassName=org.h2.Driver",
		"jdbc.url=jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1;NON_KEYWORDS=KEY,VALUE",
		"hibernate.dialect=org.hibernate.dialect.H2Dialect",
		"hibernate.hbm2ddl.auto=create"
})
class UsersApplicationTests {

	@Test
	void contextLoads() {
	}

}
