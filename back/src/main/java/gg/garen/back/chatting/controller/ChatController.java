package gg.garen.back.chatting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gg.garen.back.chatting.dto.ChatDto;
import gg.garen.back.chatting.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations template;
    public final ChatService chatService;



    // 3) TALK 타입 메세지가 WebSocket으로 발행되는 경우, 일로 온다.
    @MessageMapping("/chat/sendMessage")
    public void sendMessage (String publishMessage) {
        ChatDto msg = null;

        try{
            msg = objectMapper.readValue(publishMessage, ChatDto.class);
            chatService.saveChat(msg);
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
