package hackathon2024.hackathon2024_jh.service;

import hackathon2024.hackathon2024_jh.domain.Comment;
import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.repository.CommentRepository;
import hackathon2024.hackathon2024_jh.repository.ExpertRepository;
import hackathon2024.hackathon2024_jh.repository.MemberRepository;
import hackathon2024.hackathon2024_jh.repository.PostExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final MemberRepository memberRepository;
    private final ExpertRepository expertRepository;
    private final MemberService memberService;
    private final ExpertPostService expertPostService;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment saveGeneralComment(String token, Long postId, String content) {
        Member member = memberService.tokenToMember(token);
        ExpertPost post = expertPostService.findPost(postId);
        Comment comment = new Comment(member, post, content);
        commentRepository.saveComment(comment);
        return comment;
    }

    @Transactional
    public Comment updateComment(Long commentId, String token, String content) {
        Comment comment = commentRepository.findById(commentId);
        Member member = memberService.tokenToMember(token);
        if (member.getId() == comment.getWriterId()) {
            comment.updateComment(content);
            return comment;
        }
        return null;
    }

}
