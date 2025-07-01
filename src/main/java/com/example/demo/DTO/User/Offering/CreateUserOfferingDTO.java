package com.example.demo.DTO.User.Offering;

import java.util.List;

import com.example.demo.DTO.Categories.CategorieDTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class CreateUserOfferingDTO {

    private List<CategorieDTO> userCategories;
}
