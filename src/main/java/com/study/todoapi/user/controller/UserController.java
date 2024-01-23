package com.study.todoapi.user.controller;


import com.study.todoapi.exception.DuplicatedEmailExcetion;
import com.study.todoapi.exception.NoRegisteredArgumentsException;
import com.study.todoapi.user.Userdto.request.LoginRequestDTO;
import com.study.todoapi.user.Userdto.request.UserSignUpRequestDTO;
import com.study.todoapi.user.Userdto.response.LoginResponseDTO;
import com.study.todoapi.user.Userdto.response.UserSignUpResponseDTO;
import com.study.todoapi.user.userService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
//@CrossOrigin(origins={"http://localhost:3000"})
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    //회원가입 요청 처리
    @PostMapping
    public ResponseEntity<?> signup(@Validated @RequestBody UserSignUpRequestDTO dto, BindingResult result){

        log.info("/api/auth POST ! -{} ",dto);

        if(result.hasErrors()){
            log.warn(result.toString());
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }

        try{
            UserSignUpResponseDTO userSignUpResponseDTO = userService.create(dto);
            return ResponseEntity.ok().body(userSignUpResponseDTO);
        }catch (NoRegisteredArgumentsException e){
            log.warn("필수 가입 정보를 전달 받지 못했어요!");
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (DuplicatedEmailExcetion e){
            log.warn("이메일 중복 같은데요 ..? ");
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    //이메일 중복확인
    @GetMapping("/check")
    public ResponseEntity<?> check(String email){

        boolean flag = userService.isDuplicateEmail(email);
        log.debug("{} 중복?? -{}",email, flag);

        return ResponseEntity.ok().body(flag);
    }

    // 로그인 요청처리
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(
            @Validated @RequestBody LoginRequestDTO dto
    ){
        try {
            LoginResponseDTO authenticate = userService.authenticate(dto);
            log.info("login success! by {}",authenticate.getEmail());
            return ResponseEntity.ok().body(authenticate);
        }catch (RuntimeException e){
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
