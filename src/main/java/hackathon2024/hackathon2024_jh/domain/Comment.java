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
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private ExpertPost post;

    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Comment(Member writer, ExpertPost post, String content) {
        this.member = writer;
        this.post = post;
        this.content = content;
        this.createDate = LocalDateTime.now();
        this.updateDate = this.createDate;
    }

    public void updateComment(String content){
        this.content = content;
        this.updateDate = LocalDateTime.now();
    }

}
