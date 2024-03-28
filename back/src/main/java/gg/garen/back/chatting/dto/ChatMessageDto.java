package gg.garen.back.chatting.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatMessageDto {

    // 1) Message의 용도가 무엇인지. 입장 메세지 인지, 그냥 TALK 인지, 퇴장 메세지 인지
    private String messageType;

    // 2) Payload가 담기는 곳
    private String content;

    // 3) 보낸 이
    private String userId;

    // 4) 해당 메세지가 오고간 채팅방 번호
    private long chatRoomId;

    @Builder
    private ChatMessageDto(String messageType, String content, String userId, long chatRoomId){
        this.messageType = messageType;
        this.content = content;
        this.userId = userId;
        this.chatRoomId = chatRoomId;
    }

    public static ChatMessageDto of(String messageType, String content, String userId, long chatRoomId) {
        return  builder().messageType(messageType)
                .content(content)
                .userId(userId)
                .chatRoomId(chatRoomId)
                .build();
    }

}