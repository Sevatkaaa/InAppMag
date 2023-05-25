package com.sov.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterForm {
    private String username;
    private String password;
    private String email;
    private String role;
}
