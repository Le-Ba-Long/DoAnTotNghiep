package com.longkubi.qlns.model.dto;

import com.longkubi.qlns.model.entity.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto {
    private UUID id;
    // @NotBlank(message = "Tên Quyền  Không Được Để Trống")
    private RoleName roleName;
    @Length(max = 255, message = "Mô Tả Không Được Dài Quá 255 Kí Tự ")
    private String roleDescription;
}
