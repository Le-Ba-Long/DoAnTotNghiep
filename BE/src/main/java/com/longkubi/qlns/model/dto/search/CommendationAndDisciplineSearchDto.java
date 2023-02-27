package com.longkubi.qlns.model.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommendationAndDisciplineSearchDto extends SearchDto {
    private String code;
    private Long numberMoney;//Số Tiền Thưởng Or Phạt
    private String reason;//lí do khen thưởng kỉ luật
    private Date issuedDate;//ngày áp dụng
}
