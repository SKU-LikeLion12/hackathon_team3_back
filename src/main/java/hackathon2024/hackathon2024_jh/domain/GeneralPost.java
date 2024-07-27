package hackathon2024.hackathon2024_jh.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@Getter
public class GeneralPost extends Post {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_Id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member writer;

    private Boolean isExpertPost = false;

    public GeneralPost(String title, String content, Member writer, String category) {
        super(title, category, content);
        this.writer = writer;
    }
}
