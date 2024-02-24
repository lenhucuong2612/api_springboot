package com.example.my_demo.payloads;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min=4,message="Username must be min of 4 characters")
    private String name;
    @Email(message="Email address is not valid!")
    private String email;
    @NotEmpty
    @Size(min=3, max=10,message="Password must be min of 3 chars and max of 10 chars!")
    private String password;
    @NotEmpty
    private String about;
}
