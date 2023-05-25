package com.sov.repository;

import com.sov.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageModel, Long> {
    List<MessageModel> findAllByChatId(Long chatId);
}
