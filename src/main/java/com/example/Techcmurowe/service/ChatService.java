package com.example.Techcmurowe.service;

import com.example.Techcmurowe.model.Chat;
import com.example.Techcmurowe.model.User;
import com.example.Techcmurowe.repository.ChatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Transactional
    public Chat createChat(User user1, User user2) {
        if (!chatRepository.existsByUser1_IdAndUser2_Id(user1.getId(), user2.getId()) && !chatRepository.existsByUser1_IdAndUser2_Id(user2.getId(), user1.getId())) {
            Chat chat = new Chat();
            chat.setUser1(user1);
            chat.setUser2(user2);
            chat.setMessages(new ArrayList<>());

            return chatRepository.save(chat);
        }
        else throw new ChatAlreadyExistsException("There is a chat with those users already.");
    }

    // Definicja klasy wewnętrznej wyjątku
    public static class ChatAlreadyExistsException extends RuntimeException {
        public ChatAlreadyExistsException(String message) {
            super(message);
        }
    }
}
