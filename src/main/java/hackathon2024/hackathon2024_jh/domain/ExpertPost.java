package hackathon2024.hackathon2024_jh.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "post")
public class ExpertPost {
    /*
    Id(PK)
    category
    title
    content
    likeSize
    commentSize
    saveSize
    writerId(FK)
    IsExpertPost
    createTime
    updateTime
     */
    @Id
    @GeneratedValue
    private long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String category;



    private Long likeSize;


    private Long saveSize;


    private Long commentSize;


    private Boolean IsExpertPost;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_Id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Expert writer;


    private LocalDateTime createTime;


    private LocalDateTime updateTime;

    public ExpertPost(String title, String content, Expert writer, String category,
                      Long likeSize, Long saveSize, Long commentSize) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.category = category;
        this.likeSize = likeSize;
        this.saveSize = saveSize;
        this.commentSize = commentSize;
        this.IsExpertPost = true;
        this.createTime = LocalDateTime.now();
        this.updateTime = this.createTime;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updateTime = LocalDateTime.now();
    }

}
