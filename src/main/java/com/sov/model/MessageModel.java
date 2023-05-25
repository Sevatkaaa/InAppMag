package com.sov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "date")
    private Long date;

    @Column(name = "from_id")
    private Long fromId;

    @Column(name = "text")
    private String text;

    public MessageModel(Long chatId, Long date, Long fromId, String text) {
        this.chatId = chatId;
        this.date = date;
        this.fromId = fromId;
        this.text = text;
    }
}
