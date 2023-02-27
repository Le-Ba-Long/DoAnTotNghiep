package com.longkubi.qlns.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.model.entity.Contract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContractDto {
    private UUID id;

    @Pattern(regexp = Constant.REGEX_CODE_CONTRACT, message = "Code Chưa Đúng Format " + Constant.REGEX_CODE_CONTRACT)
    private String code;// mã hợp đồng

    //@JsonManagedReference
    private EmployeeDto employee;// nhân viên

    private Date signingDate;// ngày kí hợp đồng

    private Date contractEffect;// Hiệu lực hợp đồng

    private Byte status;// trạng thái

    private Integer basicSalary; // mức lương cơ bản

    private String nameLeader;// tên người lập hợp đồng

    private String postionLeader;// chức vụ người lập hợp đồng

    private Integer hourlyRate;//Số tiền tính cho 1h làm thêm

    private Float coefficientSalary; //Hệ Số Lương

    private String creator;

    private Date dateCreated;

    private String changedBy;

    private Date dateChange;

    public static ContractDto convertFromEntityToDto(Contract entity) {
        return new ModelMapper().map(entity, ContractDto.class);
    }
}
