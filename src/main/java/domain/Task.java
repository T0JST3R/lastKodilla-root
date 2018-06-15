package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
@Entity
public class Task {
    private Long id;
    private String title;
    private String content;
}
