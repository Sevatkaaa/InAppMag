package com.sov.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeForm {
    private Long projectId;
    private boolean positive;
}
