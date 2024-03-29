package gg.garen.back.chatting.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatDto {

    private String content;

    // 3) 보낸 이
    private String userId;

    // 4) 해당 메세지가 오고간 채팅방 번호
    private int roomId;

    @Builder
    private ChatDto(String content, String userId, int roomId){
        this.content = content;
        this.userId = userId;
        this.roomId = roomId;
    }

    public static ChatDto of(String content, String userId, int roomId) {
        return  builder()
                .content(content)
                .userId(userId)
                .roomId(roomId)
                .build();
    }

}