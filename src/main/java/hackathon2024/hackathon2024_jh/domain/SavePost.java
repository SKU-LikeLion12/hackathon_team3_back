package hackathon2024.hackathon2024_jh.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SavePost {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saver_id")
    private User saver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private boolean isSave;

    public SavePost(User user, Post post, boolean isSave) {
        this.saver = user;
        this.post = post;
        this.isSave = isSave;
    }


    public void setisSave(boolean newSaveStatus) {
        this.isSave = newSaveStatus;
    }
}
