package com.sov.controller.form;

import lombok.Data;

@Data
public class MessageForm {
    private Long chatId;
    private Long fromId;
    private String text;
}
