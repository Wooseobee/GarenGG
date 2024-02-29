package org.example.getmatches.domain.mysql;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Embeddable
public class Image {
    private String full;
    private String sprite;
    @Column(name = "image_group")
    private String group;
    private int x;
    private int y;
    private int w;
    private int h;
}
