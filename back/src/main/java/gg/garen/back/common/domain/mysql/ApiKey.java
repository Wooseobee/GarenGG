package gg.garen.back.common.domain.mysql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiKey {

    @Id
    private Long id;
    private String apiKey;
    private String uuid;
}
