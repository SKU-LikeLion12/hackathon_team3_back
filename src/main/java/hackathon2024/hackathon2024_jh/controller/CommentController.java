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
    @PostMapping("/expertpost/comment/{postId}")
    public CommentDTO.CommentResponse addComment(@PathVariable Long postId ,@RequestBody CommentDTO.CommentCreateRequest request) {
        String role = memberService.MemberOrExpert(request.getToken());
        Member member = memberService.tokenToMember(request.getToken());
        if(Objects.equals(role, "General")){
            Comment comment = commentService.saveGeneralComment(request.getToken(),postId, request.getContent());
            return new CommentDTO.CommentResponse(comment, member);
        }
        return null;
    }

    //일반회원 댓글(전문가 게시판에 대한) 수정
    @PutMapping("/expertpost/comment/{id}")
    public CommentDTO.CommentResponse updateGeneralComment(@PathVariable Long id , @RequestBody CommentDTO.CommentUpdateRequest request) {
        Member member = memberService.tokenToMember(request.getToken());
        Comment comment = commentService.updateComment(id,request.getToken(), request.getContent());
        return new CommentDTO.CommentResponse(comment,member);
    }

    //일반회원 댓글(전문가 게시판에 대한) 수정
    @DeleteMapping("/expertpost/comment/{id}")
    public String deleteGeneralComment(@PathVariable Long id, @RequestBody CommentDTO.CommentDeleteRequest request) {
        commentService.deleteComment(id,request.getToken());
        return "댓글 삭제 완료";
    }

    //특정 전문가 게시판 댓글 조회
    @GetMapping("/expertpost/comment/{postId}")
    public List<CommentDTO.CommentResponse> getGeneralCommentToExpert(@PathVariable Long postId) {
        List<CommentDTO.CommentResponse> response = new ArrayList<>();
        for (Comment comment : commentService.getExpertComments(postId)){
            Member member = memberService.MemberfindById(comment.getWriterId());
            response.add(new CommentDTO.CommentResponse(comment,member));
        }
        return response;
    }




}
