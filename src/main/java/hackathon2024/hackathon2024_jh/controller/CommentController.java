package hackathon2024.hackathon2024_jh.controller;

import hackathon2024.hackathon2024_jh.DTO.CommentDTO;
import hackathon2024.hackathon2024_jh.domain.Comment;
import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.service.CommentService;
import hackathon2024.hackathon2024_jh.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final MemberService memberService;
    @PostMapping("/expertpost/comment")
    public CommentDTO.CommentResponse addComment(@RequestBody CommentDTO.CommentCreateRequest request) {
        String role = memberService.MemberOrExpert(request.getToken());
        Member member = memberService.tokenToMember(request.getToken());
        if(Objects.equals(role, "General")){
            Comment comment = commentService.saveGeneralComment(request.getToken(), request.getPostId(), request.getContent());
            return new CommentDTO.CommentResponse(comment, member);
        }
        return null;
    }

    //일반회원 댓글(전문가 게시판에 대한) 수정
    @PutMapping("/expertpost/comment")
    public CommentDTO.CommentResponse updateGeneralComment(@RequestBody CommentDTO.CommentUpdateRequest request) {
        Member member = memberService.tokenToMember(request.getToken());
        Comment comment = commentService.updateComment(request.getCommentId(),request.getToken(), request.getContent());
        return new CommentDTO.CommentResponse(comment,member);
    }


}
