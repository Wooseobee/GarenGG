package org.example.garencrawling.mostchampion.domain;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Count {
    private Integer win = 0;
    private Integer lose = 0;
}
