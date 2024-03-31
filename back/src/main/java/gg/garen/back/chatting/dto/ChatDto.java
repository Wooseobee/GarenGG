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

    private String time = "";

    @Builder
    private ChatDto(String content, String userId, int roomId, String time){
        this.content = content;
        this.userId = userId;
        this.roomId = roomId;
        this.time = time;
    }

    public static ChatDto of(String content, String userId, int roomId, String time) {
        return  builder()
                .content(content)
                .userId(userId)
                .roomId(roomId)
                .time(time)
                .build();
    }

}