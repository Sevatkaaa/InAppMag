package com.sov.controller.form;

import lombok.Data;

@Data
public class CreateProjectForm {
    private String name;
    private int funds;
    private String description;
}
