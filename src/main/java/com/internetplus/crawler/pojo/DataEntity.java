package com.internetplus.crawler.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataEntity {
    public String punishmentName;
    public String punishmentDocNo;
    public String punishmentType;
    public String punishedPartyName;
    public String mainResponsibleName;
    public String mainIllegalFact;
    public String punishmentBasis;
    public String punishmentDecision;
    public String punisherName;
    public String punishDate;


}
