package gg.garen.back.chatting.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Document(collection = "chats")
@Getter
@Setter
@ToString
public class Chat {

    @Id
    private String id;
    private int roomId;
    private String userId;
    private String createdAt;
    private String content;

    @Builder
    public Chat(int roomId, String userId, String content) {
        this.roomId = roomId;
        this.userId = userId;
        this.content = content;
        this.createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
