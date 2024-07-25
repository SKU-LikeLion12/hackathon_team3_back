package hackathon2024.hackathon2024_jh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
//    private final ArticleRepository articleRepository;
//    private final MemberService memberService;
//    //private final LikeArticleRepository likeArticleRepository;
//
//
//    @Transactional
//    public Article saveNewArticle(String writerId, String title, String content) {
//        Member member = memberService.findByUserId(writerId);
//        Article article = new Article(title, content, member, 0L);
//        articleRepository.saveNewArticle(article);
//        return article;
//    }
//
//    @Transactional
//    public Article updateArticle(Long articleId, String title, String content, String token) {
//        Article article = articleRepository.findById(articleId);
//        Member member = memberService.tokenToMember(token);
//        if(member == article.getWriter()){
//            article.update(title, content);
//        }
//        return article;
//    }
//
//    @Transactional
//    public void deleteArticle(Long articleId, String token) {
//        Article article = articleRepository.findById(articleId);
//        Member member = memberService.tokenToMember(token);
//        if(member == article.getWriter()) {
//            articleRepository.deleteArticle(article);
//        }
//    }
//
//    public Article findArticle(Long articleId) {
//        return articleRepository.findById(articleId);
//
//    }
//
//
//    //좋아요 개수 순으로 출력하도록 수정
//    public List<Article> findAllArticles() {
//        return articleRepository.findAll();
//    }
//
//    public List<Article> findUserArticles(String memberId) {
//        Member member = memberService.findByUserId(memberId);
//        return articleRepository.findUserAll(member.getId());
//    }
}
