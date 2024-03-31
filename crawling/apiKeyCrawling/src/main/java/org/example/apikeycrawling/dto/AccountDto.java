package org.example.apikeycrawling.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AccountDto {
    private String gameName;
    private String tagLine;
}