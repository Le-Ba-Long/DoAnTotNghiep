package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.longkubi.qlns.model.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CommendationAndDisciplineDto {

    private UUID id;

    private Integer status;

    //    @Column(name = "number_money")
//    private Long numberMoney;//Số Tiền Thưởng Or Phạt
    private int type;// 1 là kỉ luật / 2 khen thưởng

    private String reason;//lí do khen thưởng kỉ luật

    private Date issuedDate;//ngày áp dụng

    private Integer day;// ngày

    private Integer month;// tháng

    private String staffName;// tên nhân viên

    private Integer year;// năm

    private String decisionNumber;// số quyết định

    private Date decisionDay;// ngày quyết định

    private Double rewardDisciplineLevel;// mức khen thưởng kỉ luật

}
