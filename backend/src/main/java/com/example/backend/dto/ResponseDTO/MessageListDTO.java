package com.example.backend.dto.ResponseDTO;

import java.util.List;

public class MessageListDTO {

    private List<MessageDTO> messages;
    
    public MessageListDTO(List<MessageDTO> messages) {
        this.messages = messages;
    }

    // Getters and Setters

    public List<MessageDTO> getMessages() {
        return messages;
    }
    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

}
