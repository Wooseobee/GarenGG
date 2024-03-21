package gg.garen.back.champion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;


@Getter
@Setter
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private String full;
    private String sprite;
    @Column(name = "image_group")
    private String group;
    private Long x;
    private Long y;
    private Long w;
    private Long h;
}
