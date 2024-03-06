package com.example.demo.DTO.User.Offering;

import java.sql.Time;
import java.util.List;

import com.example.demo.models.UserCategories;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class CreateUserOfferingDTO {

    private String location;
    private Integer experience;
    private Integer price;
    private Time workDayStart;
    private Time workDayEnd;
    private List<UserCategories> userCategories;
}
