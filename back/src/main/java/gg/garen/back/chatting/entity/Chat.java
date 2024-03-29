package gg.garen.back.chatting.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "chats")
@Getter
@Setter
@ToString
public class Chat {

    @Id
    private String id;
    private int roomId;
    private String userId;
    private LocalDateTime createdAt;
    private String content;

    @Builder
    public Chat(int roomId, String userId, String content) {
        this.roomId = roomId;
        this.userId = userId;
        this.content = content;
        this.createdAt = LocalDateTime.now(); // 객체 생성 시 현재 시간으로 초기화
    }
}
