package gg.garen.back.chatting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gg.garen.back.chatting.dto.ChatDto;
import gg.garen.back.chatting.entity.Chat;
import gg.garen.back.chatting.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations template;
    public final ChatService chatService;

    @EventListener
    public void connectEvent(SessionConnectEvent sessionConnectEvent){
//        System.out.println(sessionConnectEvent);
//        System.out.println("연결 성공 감지!_!#!#!#!@#!@@#!@!#!$!@");
        //return "redirect:chat/message";
    }

    // TALK 타입 메세지가 WebSocket으로 발행되는 경우, 일로 온다.
    @MessageMapping("/chat/sendMessage")
    @Transactional
    public void sendMessage (String publishMessage) {
        ChatDto msg = null;

        try{
            msg = objectMapper.readValue(publishMessage, ChatDto.class);
            Chat chat = chatService.saveChat(msg);
            msg.setTime(chat.getCreatedAt());
            template.convertAndSend("/sub/chat/room/" + msg.getRoomId(), msg);

        }catch (Exception e){
            log.error("Exception {}", e.getMessage());
        }

    }

    @GetMapping("/api/chat")
    public ResponseEntity<List<ChatDto>> getChatList() {
        try {
            List<ChatDto> list;
            list = chatService.getChat();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
