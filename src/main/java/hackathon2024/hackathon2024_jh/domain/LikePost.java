package hackathon2024.hackathon2024_jh.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class LikePost {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liker_id")
    private User liker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private boolean isLike;

    public LikePost(User user, Post post, boolean isLike) {
        this.liker = user;
        this.post = post;
        this.isLike = isLike;
    }

    public void setisLike(boolean newLikeStatus) {
        this.isLike = newLikeStatus;
    }
}
