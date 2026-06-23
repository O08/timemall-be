package com.norm.timemall.app.team.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeamAppMeetrEditEventsDTO {
    @NotBlank(message = "id required")
    private String id;

    @NotNull(message = "eventType required")
    private AppMeetrEventTypeEnum eventType;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 32)
    private String title;

    @NotNull(message = "category required")
    private AppMeetrEventCategoryEnum category;


    @NotBlank(message = "description required")
    @Length(message = "description length must in range {min}-{max}",min = 1,max = 1000)
    private String description;

    @NotBlank(message = "location required")
    @Length(min = 2, max = 255, message = "location length must be between {min} and {max} characters")
    private String location;


    @NotNull(message = "activityStartAt required")
    @Future(message = "activity start time must be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime activityStartAt;

    @NotEmpty(message = "topics required")
    @Size(min = 1, max = 5, message = "You can select up to 5 topics")
    private List<
            @NotBlank(message = "Topic content cannot be blank")
            @Length(max = 20, message = "Each topic name cannot exceed 20 characters")
                    String> topics;



    @NotNull(message = "duration required")
    @Min(value = 1, message = "duration must be at least 1")
    @Max(value = 365, message = "duration cannot exceed 365")
    private Integer duration;


    @NotNull(message = "durationType required")
    private AppMeetrDurationTypeEnum durationType;


    @NotNull(message = "maxSeats required")
    @Min(value = 0, message = "maxSeats must be at least 0")
    @Max(value = 999999, message = "maxSeats is too large")
    private Integer maxSeats;


    @NotNull(message = "budget required")
    @DecimalMin(value = "0.00", message = "budget cannot be negative")
    @DecimalMax(value = "50000.00", message = "budget cannot exceed 50000")
    private BigDecimal budget;


    @Length(message = "onlineLink length must in range {min}-{max}",min = 0,max = 500)
    private String onlineLink;

    @NotBlank(message = "allowGuests required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: allowGuests, incorrect parameter value ,option: on-1; off-0;")
    private String allowGuests;

    @NotNull(message = "eventStatus required")
    private AppMeetrEventStatusEnum eventStatus;
}
