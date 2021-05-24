package com.adjourtechnologie.reseauenset.service;

import com.adjourtechnologie.reseauenset.model.Message;
import org.springframework.http.ResponseEntity;

public interface TchatService {
    ResponseEntity<Message> sendMessage(Message message);
    void deleteMessage(Message message);
}
