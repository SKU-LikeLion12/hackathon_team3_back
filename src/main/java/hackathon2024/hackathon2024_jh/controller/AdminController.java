package hackathon2024.hackathon2024_jh.controller;
import hackathon2024.hackathon2024_jh.DTO.AdminDTO;
import hackathon2024.hackathon2024_jh.DTO.MessageDTO;
import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class AdminController {
    @Value("${admin.id}")
    private String adminId;

    @Value("${admin.pw}")
    private String adminPw;


    private final AdminService adminService;
    @PostMapping("/admin/login")
    public String adminLogin(@RequestBody AdminDTO.AdminLoginRequest request) {

        if (request.getPassword().equals(adminPw) && request.getId().equals(adminId)){
            return "success";
        }
        return "fail";
    }


    @GetMapping("/admin/expertCheck")
    public List<AdminDTO.expertList> expertCheck(){
        List<AdminDTO.expertList> list = new ArrayList<>();
        for (Expert expert : adminService.findAll()){
            list.add(new AdminDTO.expertList(expert.getId(), expert.getUserId(),
                    expert.getNickname(), expert.getGender(),
                    expert.getPhoneNum(), expert.getEmail(), expert.getIsExpert(),
                    expert.getBirth(),expert.getCreateTime(), expert.getUpdateTime()));
        }
        return list;
    }


    @PostMapping("/admin/changeIsExpert/{id}")
    public AdminDTO.expertList changeIsExpert(@PathVariable("id") Long id, @RequestBody boolean isExpert){
        Expert expert = adminService.findById(id);
        Expert newExpert = adminService.changeIsExpert(expert, isExpert);

        return new AdminDTO.expertList(newExpert.getId(), newExpert.getUserId(),
                newExpert.getNickname(), newExpert.getGender(),
                newExpert.getPhoneNum(), newExpert.getEmail(), newExpert.getIsExpert(),
                newExpert.getBirth(),newExpert.getCreateTime(), newExpert.getUpdateTime());
    }


}
