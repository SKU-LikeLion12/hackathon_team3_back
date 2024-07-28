package hackathon2024.hackathon2024_jh.service;

import hackathon2024.hackathon2024_jh.domain.*;
import hackathon2024.hackathon2024_jh.repository.CommentRepository;
import hackathon2024.hackathon2024_jh.repository.ExpertRepository;
import hackathon2024.hackathon2024_jh.repository.MemberRepository;
import hackathon2024.hackathon2024_jh.repository.PostExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final MemberRepository memberRepository;
    private final ExpertRepository expertRepository;
    private final MemberService memberService;
    private final ExpertPostService expertPostService;
    private final CommentRepository commentRepository;
    private final GeneralPostService generalPostService;

    // 일반게시글 + 전문가 게시글에 대한 댓글 작성
    @Transactional
    public Comment saveGeneralComment(User user, Long postId, String content) {

        Post post = generalPostService.findPost(postId);
        if (post == null) {
            post = expertPostService.findPost(postId);
        }
        Comment comment = new Comment(user, post, content);
        if(commentRepository.findFirstExpertComment(post)==null){
            comment.fixComment();
        }
        commentRepository.saveComment(comment);
        return comment;
    }


    //(일반회원) 댓글 수정
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

    //댓글 삭제 (일반회원)
    @Transactional
    public void deleteComment(Long commentId, String token) {
        Comment comment = commentRepository.findById(commentId);
        String role = memberService.MemberOrExpert(token);
        if(Objects.equals(role, "General")){
            Member member = memberService.tokenToMember(token);
            if (member.getId() == comment.getWriterId()) {
                commentRepository.deleteComment(comment);
            }
        }
        else{
            Expert expert = memberService.tokenToExpert(token);
            if (expert.getId() == comment.getWriterId()) {
                commentRepository.deleteComment(comment);
            }
        }



    }

    //전문가 개시판에 달린 일반 댓글 조회
    public List<Comment> getExpertComments(Long postId) {
        Post post = expertPostService.findPost(postId);
        return commentRepository.findPostsComments(post);
    }

    //일반 개시판에 달린 모든 댓글 조회
    public List<Comment> getAllCommentsFromGeneralPost(Long postId) {
        Post post = generalPostService.findPost(postId);
        return commentRepository.findPostsComments(post);
    }

    public Comment getFirstCommentFromGeneralPost(Long postId) {
        Post post = generalPostService.findPost(postId);
        return commentRepository.findFirstExpertComment(post);
    }


}
