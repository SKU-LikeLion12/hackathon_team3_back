package hackathon2024.hackathon2024_jh.controller;

import hackathon2024.hackathon2024_jh.DTO.CommentDTO;
import hackathon2024.hackathon2024_jh.domain.Comment;
import hackathon2024.hackathon2024_jh.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
//    private final CommentService commentService;
//    @PostMapping("/comment")
//    public CommentDTO.CommentResponse createComment(@RequestBody CommentDTO.CommentCreateRequest request) {
//        Comment comment = commentService.saveComment(request.getToken(), request.getArticleId(), request.getContent());
//        return new CommentDTO.CommentResponse(comment);
//    }
//
//    @PutMapping("/comment")
//    public CommentDTO.CommentResponse updateComment(@RequestBody CommentDTO.CommentUpdateRequest request) {
//        Comment comment = commentService.updateComment(request.getCommentId(), request.getToken(), request.getContent());
//        if(comment == null ) return null;
//        return new CommentDTO.CommentResponse(comment);
//    }
//
//    @DeleteMapping("/comment")
//    public void deleteComment(@RequestBody CommentDTO.CommentDeleteRequest request) {
//        commentService.deleteComment(request.getCommentId(), request.getToken());
//    }
//
//    @GetMapping("/comment/article/{id}")
//    public List<CommentDTO.CommentResponse> articleComments(@PathVariable("id") Long articleId) {
//        List<CommentDTO.CommentResponse> response = new ArrayList<>();
//        for (Comment comment : commentService.articleToComment(articleId)){
//            response.add(new CommentDTO.CommentResponse(comment));
//        }
//        return response;
//    }

}
