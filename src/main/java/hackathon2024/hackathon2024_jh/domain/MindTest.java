package hackathon2024.hackathon2024_jh.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "mind_test", uniqueConstraints = @UniqueConstraint(columnNames = {"tester_id", "category"}))
public class MindTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    @Setter
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tester_id")
    private Member tester;

    @Setter
    private LocalDateTime testTime;

    public MindTest(Member tester, String category, int score) {
        this.tester = tester;
        this.category = category;
        this.score = score;
        this.testTime = LocalDateTime.now();
    }


}
