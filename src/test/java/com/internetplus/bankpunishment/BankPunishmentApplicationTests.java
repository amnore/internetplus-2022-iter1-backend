package com.internetplus.bankpunishment;

import com.internetplus.bankpunishment.bl.BankPunishmentBl;
import com.internetplus.bankpunishment.data.BankPunishmentMapper;
import com.internetplus.bankpunishment.entity.BankPunishment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import sun.jvm.hotspot.utilities.Assert;

import java.util.List;

@SpringBootTest
class BankPunishmentApplicationTests {
	@Autowired
	BankPunishmentBl bankPunishmentBl;
	@Autowired
	BankPunishmentMapper bankPunishmentMapper;

	@Test
	void contextLoads() {
		//肉眼比对搜索全体的数量是否搜对
		int numInAll = bankPunishmentBl.selectBankPunishment(new BankPunishment()).size();
		System.out.println("initial size "+numInAll);//搜索全体

		BankPunishment bankPunishment = new BankPunishment();
		bankPunishment.setPunisherName("aa");//搜索该字段
		int count = bankPunishmentBl.selectBankPunishment(bankPunishment).size();

		//测插入和搜索全体
		bankPunishmentBl.insertBankPunishment(bankPunishment);
		int id = bankPunishment.getId();
		assert bankPunishmentBl.selectBankPunishment(new BankPunishment()).size()==numInAll+1:"insert";

		//测选择
		bankPunishment.setId(null);
		assert bankPunishmentBl.selectBankPunishment(bankPunishment).size()==count+1:"select";

		//测全字段更新
		bankPunishment.setId(id);
		bankPunishment.setPunisherName("ss");
		bankPunishment.setStatus("0");
		bankPunishmentBl.updateBankPunishment(bankPunishment);
		assert bankPunishmentMapper.selectBankPunishmentById(id).getPunisherName().equals("ss"):"update";
		assert bankPunishmentMapper.selectBankPunishmentById(id).getStatus().equals("0"):"update";

		//测部分字段更新
		bankPunishment.setPunisherName("aa");
		bankPunishment.setStatus(null);
		bankPunishmentBl.updateBankPunishmentExceptNull(bankPunishment);
		assert bankPunishmentMapper.selectBankPunishmentById(id).getPunisherName().equals("aa"):"updateExceptNull";
		assert bankPunishmentMapper.selectBankPunishmentById(id).getStatus().equals("0"):"updateExceptNull";

		//测：仅有id的部分字段更新不报错
		bankPunishment.setPunisherName(null);
		bankPunishmentBl.updateBankPunishmentExceptNull(bankPunishment);

		//测删除记录
		bankPunishmentBl.deleteBankPunishment(id);
		assert bankPunishmentBl.selectBankPunishment(new BankPunishment()).size()==numInAll:"delete";

	}

}
