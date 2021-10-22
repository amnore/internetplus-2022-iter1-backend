package com.internetplus.bankpunishment;

import com.internetplus.bankpunishment.bl.BankPunishmentBl;
import com.internetplus.bankpunishment.entity.BankPunishment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BankPunishmentApplicationTests {
	@Autowired
	BankPunishmentBl bankPunishmentBl;

	@Test
	void contextLoads() {
		BankPunishment bankPunishment = new BankPunishment();
		bankPunishment.setId(19);
//		bankPunishment.setStatus("1");
//		bankPunishment.setPunisherName("hhhh");
//		bankPunishment.setMainIllegalFact("testre~");
		bankPunishmentBl.deleteBankPunishment(19);
	}

}
