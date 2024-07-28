package hackathon2024.hackathon2024_jh.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long likeSize = 0L;
    private Long saveSize = 0L;
    private Long commentSize = 0L;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Post(String title, String category, String content) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.createTime = LocalDateTime.now();
        this.updateTime = this.createTime;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updateTime = LocalDateTime.now();
    }

    public void setLikeSize(Long likeSize) {
        this.likeSize = likeSize;
    }
}
