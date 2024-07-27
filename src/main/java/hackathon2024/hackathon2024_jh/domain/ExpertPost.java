package hackathon2024.hackathon2024_jh.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@Getter
public class ExpertPost extends Post {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_Id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Expert writer;

    private Boolean isExpertPost = true;

    public ExpertPost(String title, String content, Expert writer, String category) {
        super(title, category, content);
        this.writer = writer;
    }
}