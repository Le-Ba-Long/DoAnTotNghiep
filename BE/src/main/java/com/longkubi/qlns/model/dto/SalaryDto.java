package com.longkubi.qlns.model.dto;

import com.longkubi.qlns.model.entity.Salary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.UUID;

import static com.longkubi.qlns.common.Constant.REGEX_CODE_SALARY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalaryDto {

    private UUID id;
    @Pattern(regexp = REGEX_CODE_SALARY,message = "Mã Lương Không Đúng Định Dạng "+ REGEX_CODE_SALARY)
    private String code;

    private long hourlyRate;//Số tiền tính cho 1h làm thêm

    private long salaryLevel;//Mức Lương Cơ Bản

    private double coefficientSalary; //Hệ Số Lương

    //    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinColumn(name = "employee_code")
//    private Employee employee;
    private String creator;

    private Date dateCreated;

    private String changedBy;

    private Date dateChange;
    public  static SalaryDto convertFromEntityToDto(Salary entity){
        return new ModelMapper().map(entity, SalaryDto.class);
    }
}
