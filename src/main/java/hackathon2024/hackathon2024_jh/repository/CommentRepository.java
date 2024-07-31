package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Comment;
import hackathon2024.hackathon2024_jh.domain.Post;

import java.util.List;

public interface CommentRepository {
    public void saveComment(Comment comment);
    public Comment findById(Long id);
    public void deleteComment(Comment comment);

    List<Comment> findPostsComments(Post Post);

    Comment findFirstExpertComment(Post post);


    Long countCommentSize(Post post);
}
