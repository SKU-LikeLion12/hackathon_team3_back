package hackathon2024.hackathon2024_jh.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;
    private Boolean isFix;
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime updateDate = LocalDateTime.now();

    @Column(name = "writer_type")
    @Enumerated(EnumType.STRING)
    private WriterType writerType;

    @Column(name = "writer_id")
    private Long writerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Comment(User user, Post post, String content) {
        if (user instanceof Member) {
            this.writerType = WriterType.MEMBER; // 'MEMBER'
        } else if (user instanceof Expert) {
            this.writerType = WriterType.EXPERT; // 'EXPERT'
        }
        this.writerId = user.getId();
        this.post = post;
        this.content = content;
        this.isFix = false;
    }



    public void updateComment(String content) {
        this.content = content;
        this.updateDate = LocalDateTime.now();
    }

    public void fixComment() {
        this.isFix = true;
    }
}


