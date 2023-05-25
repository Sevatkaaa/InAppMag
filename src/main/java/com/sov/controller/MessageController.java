package com.sov.controller;

import com.sov.controller.form.MessageForm;
import com.sov.data.ChatInfoData;
import com.sov.data.InvestorData;
import com.sov.data.MessageData;
import com.sov.data.ProjectData;
import com.sov.model.InterestModel;
import com.sov.model.InvestorModel;
import com.sov.model.UserModel;
import com.sov.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    @Resource
    private MessageService messageService;

    @Resource
    private InvestorService investorService;

    @Resource
    private ProjectService projectService;

    @Resource
    private UserService userService;

    @Resource
    private InterestService interestService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ChatInfoData> getMessages(@RequestParam Long chatId) {
        ChatInfoData chatInfoData = new ChatInfoData();
        List<MessageData> messages = messageService.getMessagesByChatId(chatId).stream().map(MessageData::from).collect(Collectors.toList());
        messages.sort(Comparator.comparing(MessageData::getDate));
        chatInfoData.setMessages(messages);
        InterestModel chat = interestService.getInterestById(chatId);
        chatInfoData.setInvestorData(InvestorData.from(investorService.getInvestorByUsername(chat.getInvestorName()).orElse(null)));
        chatInfoData.setProjectData(ProjectData.from(projectService.getProjectById(chat.getProjectId()).orElse(null)));
        chatInfoData.setConfirmed(chat.getConfirmed() != null && chat.getConfirmed());
        return ResponseEntity.ok(chatInfoData);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity sendMessage(@RequestBody MessageForm message) {
        InterestModel interest = interestService.getInterestById(message.getChatId());
        if (message.getFromId() == null) {
            message.setFromId(investorService.getCurrentInvestor().map(InvestorModel::getId).orElse(null));
        }
        messageService.addMessage(interest.getId(), message.getFromId(), message.getText());
        return ResponseEntity.ok().build();
    }


}
