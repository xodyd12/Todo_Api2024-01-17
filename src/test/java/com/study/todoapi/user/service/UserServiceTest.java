//package com.study.todoapi.user.service;
//
//import com.study.todoapi.user.dto.request.UserSignUpRequestDTO;
//import com.study.todoapi.user.dto.response.UserSignUpResponseDTO;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//@Transactional
//@Rollback(false)
//class UserServiceTest {
//
//    @Autowired
//    UserService userService;
//
//    @Test
//    @DisplayName("회원가입 하면 비밀번호가 인코딩 되어 디비에 저장 ")
//    void saveTest() {
//        //given
//        UserSignUpRequestDTO build = UserSignUpRequestDTO.builder()
//                .email("bbb123@naver.com")
//                .password("bbb1234!")
//                .userName("외계인")
//                .build();
//        //when
//
//        UserSignUpResponseDTO userSignUpResponseDTO = userService.create(build);
//        //then
//        assertEquals("외계인",userSignUpResponseDTO.getUserName());
//
//        System.out.println("\n\n\n");
//        System.out.println("responseDTO = " + userSignUpResponseDTO);
//        System.out.println("\n\n\n");
//
//
//
//    }
//
//
//}