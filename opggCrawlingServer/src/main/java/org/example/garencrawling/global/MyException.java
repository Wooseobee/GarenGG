package org.example.garencrawling.global;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MyException extends Exception {
    String message;
}
