package hackathon2024.hackathon2024_jh.service;

import hackathon2024.hackathon2024_jh.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;

}
