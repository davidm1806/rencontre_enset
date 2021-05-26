package com.adjourtechnologie.reseauenset.service;

import com.adjourtechnologie.reseauenset.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TchatService {
    ResponseEntity<Message> sendMessage(Message message);
    ResponseEntity<Message> deleteMessage(Message message);
    ResponseEntity<?> setIsEpeingler(Long accountId, Long messageId);
    Page<Message> findMessagesByGroupeId(Long groupId, Pageable pageable);
}
