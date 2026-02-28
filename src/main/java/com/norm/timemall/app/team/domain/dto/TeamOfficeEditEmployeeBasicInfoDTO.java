package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.OfficeEmployeeGenreEnum;
import com.norm.timemall.app.base.enums.OfficeEmployeeStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TeamOfficeEditEmployeeBasicInfoDTO {
    @NotBlank(message = "employeeId required")
    private String employeeId;

    @NotBlank(message = "employeeNumber required")
    @Length(message = "employeeNumber length must in range {min}-{max}",min = 0,max = 30)
    @Pattern(regexp="^\\w+$", message="员工编号字符不符合要求")
    private String employeeNumber;
    @NotBlank(message = "departmentId required")
    private String departmentId;
    @NotBlank(message = "role required")
    @Length(message = "role length must in range {min}-{max}",min = 0,max = 30)
    private String role;

    @Length(message = "level length must in range {min}-{max}",min = 0,max = 10)
    private String level;
    @NotBlank(message = "employeeName required")
    @Length(message = "employeeName length must in range {min}-{max}",min = 0,max = 16)
    private String employeeName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @Length(message = "education length must in range {min}-{max}",min = 0,max = 32)
    private String education;

    @Length(message = "major length must in range {min}-{max}",min = 0,max = 100)
    private String major;

    @Length(message = "gender length must in range {min}-{max}",min = 0,max = 8)
    private String gender;

    @NotNull(message = "hireDate required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    @Positive(message = "netWorth must be positive")
    private BigDecimal netWorth;

    @NotBlank(message = "officeLocation required")
    @Length(message = "officeLocation length must in range {min}-{max}",min = 0,max = 80)
    private String officeLocation;

    @Length(message = "phone length must in range {min}-{max}",min = 0,max = 80)
    private String phone;

    @Length(message = "email length must in range {min}-{max}",min = 0,max = 80)
    @Email(message = "邮箱格式不符合标准")
    private String email;

    @NotNull(message = "salary required")
    @Positive(message = "salary must be positive")
    private BigDecimal salary;

    @EnumCheck(enumClass = OfficeEmployeeStatusEnum.class,message = "field: status, incorrect parameter value ")
    @NotBlank(message = "status required")
    private String status;

    @NotBlank(message = "genre required")
    @EnumCheck(enumClass = OfficeEmployeeGenreEnum.class,message = "field: genre, incorrect parameter value ")
    private String genre;


    @Length(message = "email length must in range {min}-{max}",min = 0,max = 400)
    private String remark;


}
