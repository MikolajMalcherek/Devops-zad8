package com.example.Techcmurowe.controller;

import com.example.Techcmurowe.model.Chat;
import com.example.Techcmurowe.model.Message;
import com.example.Techcmurowe.model.User;
import com.example.Techcmurowe.repository.ChatRepository;
import com.example.Techcmurowe.repository.MessageRepository;
import com.example.Techcmurowe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/messages/")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/messages/{chatId}")
    public Message sendMessage(@DestinationVariable Long chatId, Message message) {
        Chat chat = chatRepository.findById(chatId).orElseThrow();
        message.setDatetime(LocalDateTime.now());
        message.setChat(chat);
        return messageRepository.save(message);
    }

    @GetMapping("/{chatId}")
    public List<Message> getMessagesForChat(@PathVariable Long chatId) {
        List<Message> messages = messageRepository.findAllByChatId(chatId);

        return messages;
    }
}
