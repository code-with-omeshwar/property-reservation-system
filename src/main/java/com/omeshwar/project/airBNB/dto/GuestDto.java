package com.omeshwar.project.airBNB.dto;

import com.omeshwar.project.airBNB.entity.User;
import com.omeshwar.project.airBNB.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GuestDto {
    private Long id;
    private String name;
    private Integer age;
    private Gender gender;
    private LocalDateTime createdAt;
}
