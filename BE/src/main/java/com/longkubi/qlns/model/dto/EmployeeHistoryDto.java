package com.longkubi.qlns.model.dto;

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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class EmployeeHistoryDto {

    private UUID id;

   // private String workingPosition;// chức vụ làm việc

    private Date endDate;// ngày nghỉ việc

  //  private String titleRecruit;// ví trí làm việc

    private String description;// Mô Tả Chi Tiết Sự Kiện Xảy Ra

   // private String workingDepartment;// phòng ban làm việc

    private String event;// nội dung sự kiện

    private Date date;// ngày xảy ra sự kiện

    private byte status;// trạng thái

    private EmployeeDto employeeDto;
    // private Date rewardOrDisciplineDay;//ngày khen thưởng hoặc kỉ luật
    // private Date fromDate;// giữ chức vụ từ ngày

    //private Date toDate;// giữ chức vụ đến ngày

    //private Date startDate;// ngày vào cty
}
