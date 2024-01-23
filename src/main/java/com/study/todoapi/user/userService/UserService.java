package com.study.todoapi.user.userService;


import com.study.todoapi.auth.TokenProvider;
import com.study.todoapi.exception.DuplicatedEmailExcetion;
import com.study.todoapi.exception.NoRegisteredArgumentsException;
import com.study.todoapi.user.Userdto.request.LoginRequestDTO;
import com.study.todoapi.user.Userdto.request.UserSignUpRequestDTO;
import com.study.todoapi.user.Userdto.response.LoginResponseDTO;
import com.study.todoapi.user.Userdto.response.UserSignUpResponseDTO;
import com.study.todoapi.user.entity.User;
import com.study.todoapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    //會原加入 處理
    public UserSignUpResponseDTO create(UserSignUpRequestDTO dto){

        if(dto == null){
            throw new NoRegisteredArgumentsException("회원가입 입력정보가 일치하지 않음");
        }
        String email = dto.getEmail();

        if (userRepository.existsByEmail(email)){
            log.warn("이메일이 중복 되었습니다. : {}", email);
            throw new DuplicatedEmailExcetion("중복된 이메일 입니다 ! ");
        }

        User save = userRepository.save(dto.toEntity(passwordEncoder));

        log.info("회원가입 성공 !  save user - {}",save);

        return new UserSignUpResponseDTO(save); //회원가입 정보를 클라이언트에게 리턴


    }
    // 이메일 중복확인
    public boolean isDuplicateEmail(String email){
        return userRepository.existsByEmail(email);
    }

    //회원 인증 (login)
    public LoginResponseDTO authenticate(final LoginRequestDTO dto){

        //이메일을 통해 회원 정보 조회
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new RuntimeException("가입된 회원이 아닙니다!")
        );

        //패스워도 검증
        String password = dto.getPassword();//입력한 비밀번호
        String encodedPassword = user.getPassword(); //DB에 저장된 비번

        if(!passwordEncoder.matches(password, encodedPassword)){
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        //로그인 성공 후 어떻게 할 것인가? 세션에 저장할 것인가? 토큰을 발급할 것인가?]
        String token = tokenProvider.createToken(user);

        //클라이언트에게 토큰을 발급해서 제공
        return new LoginResponseDTO(user,token);


    }
}
