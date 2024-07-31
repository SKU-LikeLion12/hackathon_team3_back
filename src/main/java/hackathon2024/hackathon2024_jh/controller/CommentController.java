package hackathon2024.hackathon2024_jh.controller;

import hackathon2024.hackathon2024_jh.DTO.CommentDTO;
import hackathon2024.hackathon2024_jh.domain.Comment;
import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.domain.User;
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
        User user = memberService.tokenToMember(request.getToken());
        if(Objects.equals(role, "General")){
            Comment comment = commentService.saveGeneralComment(user,postId, request.getContent());
            return new CommentDTO.CommentResponse(comment, user);
        }
        return null;
    }

    //댓글 수정(공통)
    @PutMapping("/comment/{id}")
    public CommentDTO.CommentResponse updateComment(@PathVariable Long id , @RequestBody CommentDTO.CommentUpdateRequest request) {
        String role = memberService.MemberOrExpert(request.getToken());
        if(Objects.equals(role, "General")){
            Member member = memberService.tokenToMember(request.getToken());
            Comment comment = commentService.updateComment(id,request.getToken(), request.getContent());
            return new CommentDTO.CommentResponse(comment,member);
        }
        else{
            Expert expert = memberService.tokenToExpert(request.getToken());
            Comment comment = commentService.updateComment(id,request.getToken(), request.getContent());
            return new CommentDTO.CommentResponse(comment,expert);
        }



    }

    //댓글 삭제(공통)
    @DeleteMapping("/comment/{id}")
    public String deleteExpertPostComment(@PathVariable Long id, @RequestBody CommentDTO.CommentDeleteRequest request) {
        commentService.deleteComment(id,request.getToken());
        return "댓글 삭제 완료";
    }

    //특정 전문가 게시판 댓글 조회
    @GetMapping("/expertpost/comment/{postId}")
    public List<CommentDTO.CommentResponse> getGeneralCommentToExpert(@PathVariable Long postId) {
        List<CommentDTO.CommentResponse> response = new ArrayList<>();
        for (Comment comment : commentService.getExpertComments(postId)){
            User user = memberService.UserfindById(comment.getWriterId());
            response.add(new CommentDTO.CommentResponse(comment,user));
        }
        return response;
    }

    @PostMapping("/generalpost/comment/{postId}")
    public CommentDTO.CommentResponse addCommentToGeneral(@PathVariable Long postId ,@RequestBody CommentDTO.CommentCreateRequest request) {
        String role = memberService.MemberOrExpert(request.getToken());
        if(Objects.equals(role, "General")){
            Member member = memberService.tokenToMember(request.getToken());
            Comment comment = commentService.saveGeneralComment(member,postId, request.getContent());
            return new CommentDTO.CommentResponse(comment, member);
        }
        else{
            Expert expert = memberService.tokenToExpert(request.getToken());
            Comment comment = commentService.saveGeneralComment(expert ,postId, request.getContent());
            return new CommentDTO.CommentResponse(comment, expert);
        }

    }

    @GetMapping("/generalpost/comment/{postId}")
    public List<CommentDTO.CommentResponse> getCommentToGeneral(@PathVariable Long postId) {
        List<CommentDTO.CommentResponse> response = new ArrayList<>();
        Comment fixedComment = commentService.getFirstCommentFromGeneralPost(postId);
        if (fixedComment!=null){
            User fixedExpert = memberService.UserfindById(fixedComment.getWriterId());
            response.add(new CommentDTO.CommentResponse(fixedComment,fixedExpert));
        }

        System.out.println(response);
        for (Comment comment : commentService.getAllCommentsFromGeneralPost(postId)){
            if(fixedComment != null && comment==fixedComment){
                continue;
            }
            User user = memberService.UserfindById(comment.getWriterId());
            response.add(new CommentDTO.CommentResponse(comment,user));
        }
        return response;
    }





}
