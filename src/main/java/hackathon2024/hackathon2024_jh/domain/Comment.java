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
    private Boolean isFix = false;
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

    public Comment(Member member, Post post, String content) {
        this.writerType = WriterType.MEMBER;
        this.writerId = member.getId();
        this.post = post;
        this.content = content;
    }

    public Comment(Expert expert, Post post, String content) {
        this.writerType = WriterType.EXPERT;
        this.writerId = expert.getId();
        this.post = post;
        this.content = content;
    }

    public void updateComment(String content) {
        this.content = content;
        this.updateDate = LocalDateTime.now();
    }
}


