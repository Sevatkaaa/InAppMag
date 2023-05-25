package com.sov.service;

import com.sov.model.MessageModel;
import com.sov.repository.MessageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    @Resource
    private MessageRepository messageRepository;

    public void addMessage(Long chatId, Long fromId, String text) {
        messageRepository.save(new MessageModel(chatId, new Date().getTime(), fromId, text));
    }

    public List<MessageModel> getMessagesByChatId(Long chatId) {
        return messageRepository.findAllByChatId(chatId);
    }
}
