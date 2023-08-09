package com.vtm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private String passwordConfirm;
    private String companyName;
    private String role;
}
