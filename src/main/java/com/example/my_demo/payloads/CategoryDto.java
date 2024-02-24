package com.example.my_demo.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private int id;
    @NotBlank
    @Size(min=4, message="Min size of category title is 4")
    private String categoryDtoTitle;

    @NotBlank
    @Size(min=4, message="Min size of category title is 4")
    private String categoryDtoDescription;
}
