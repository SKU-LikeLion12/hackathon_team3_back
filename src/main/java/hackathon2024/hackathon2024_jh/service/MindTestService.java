package hackathon2024.hackathon2024_jh.service;

import hackathon2024.hackathon2024_jh.DTO.MindTestDTO;
import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.domain.MindTest;
import hackathon2024.hackathon2024_jh.repository.MindTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MindTestService {
    private final MindTestRepository mindTestRepository;

    @Transactional
    public MindTest saveMindTest(Member member, String category, int score){
        MindTest mindTest = new MindTest(member, category, score);
        mindTestRepository.save(mindTest);
        return mindTest;
    }

    @Transactional
    public List<MindTestDTO.MindTestResponse> getMindTest(Member member){
        List<MindTest> mindTestList = mindTestRepository.findTestByUser(member);
        List<MindTestDTO.MindTestResponse> mindTestResponseList = new ArrayList<>();
        for(MindTest mindTest : mindTestList){
            mindTestResponseList.add(new MindTestDTO.MindTestResponse(mindTest, member));
        }
        return mindTestResponseList;
    }

}
