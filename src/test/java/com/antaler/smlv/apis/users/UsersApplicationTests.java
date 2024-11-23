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
		"PUB_KEY=MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCF82q/OiWAEebxUXKaKmNtkXeE8o4dTI/k7zimJEZjgmEaBrojDR8+Wgw3HmBemC0ez+QlNFVc9h9G228QPpCBrx4p2vXXjeBXX27PS27b6X+7+qlvcH6zkZnSWfnemgGbslUIujMr2Q+tPkkSDEolmy1KnlA0IkQqCA7fiRyAzwIDAQAB",
		"PRIV_KEY=MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIXzar86JYAR5vFRcpoqY22Rd4Tyjh1Mj+TvOKYkRmOCYRoGuiMNHz5aDDceYF6YLR7P5CU0VVz2H0bbbxA+kIGvHina9deN4Fdfbs9Lbtvpf7v6qW9wfrORmdJZ+d6aAZuyVQi6MyvZD60+SRIMSiWbLUqeUDQiRCoIDt+JHIDPAgMBAAECgYAq76aE20+P6K0dJx1T+0hZo6oq7FDPZW4uVNvUrYX+eZlEbWmWuKsOInEN7ee4CpFT8hFQN6ExJhdKcCX+j/9jiv8YFPM3sknC1uACVS5sV0wtsij3+A0Kt6SSTkN7dL1yCFMrPMdHtv2F+vGuX409NMZSmiLMtZKoiOx4xhqjQQJBAKebrMtMI8Wb1hotLmJhxtuHcmblFB6e1qBLXFZlds2mU0aK5LSaqHDzN1uWnwbYM/UczgFwU62OHV2mmlA9SW8CQQDMl8PZ/YM2mQpiYjyjYmSZRDV3K2poB3vhtFDPlTaTGyLqAHkbqLcZIMyZLjkMM3q1iOJKZ3b3PD61SilVJs6hAkAotQGBmqtxegjE8MY8NL0kYkAhx29chH1iDwuNHDV/eu7syh0D1DAmxptiTjGvauErneRWqx5Xn5V4QldmN4xXAkEAn4YGM8aztLKHokauuUZS4h6JAa5cfVi4gzgxemoB75nNE1/jDmnyuikU3qtn19eb6kgdCZ665/OVgY2dxMNJgQJAKIILYscVjXV7Ox95Lkwh2DUGL2XEHEm3GIxOJF9IlpOlFhj9+n6VLgAvRnb73ROaXlBY+uGKWoOpumy/tZLL4g==",
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
