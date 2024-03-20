package gg.garen.back.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveGameRequestDto {
    private String name;
    private String description;
}
