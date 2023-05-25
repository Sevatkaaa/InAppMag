package com.sov.data;

import com.sov.model.MessageModel;
import lombok.Data;

@Data
public class MessageData {
    private Long id;
    private Long chatId;
    private Long date;
    private Long fromId;
    private String text;


    public static MessageData from(MessageModel messageModel) {
        MessageData messageData = new MessageData();
        messageData.setId(messageModel.getId());
        messageData.setChatId(messageModel.getChatId());
        messageData.setFromId(messageModel.getFromId());
        messageData.setDate(messageModel.getDate());
        messageData.setText(messageModel.getText());
        return messageData;
    }
}
