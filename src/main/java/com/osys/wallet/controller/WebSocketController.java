// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.controller;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send-message")
    public void sendMessage(Item item) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String message = " REPO UPDATERD has been saved.";

        messagingTemplate.convertAndSendToUser(username, "/topic/messages", message);
    }

}
