package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Comment;

public interface CommentRepository {
    public void saveComment(Comment comment);
    public Comment findById(Long id);
    public void deleteComment(Comment comment);

}
