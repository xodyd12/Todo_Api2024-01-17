package com.study.todoapi.todo.user.repository;

import com.study.todoapi.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import com.study.todoapi.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void saveTest() {
        //given
        User user = User.builder()
                .email("abc1234@abc.com")
                .password("1234")
                .userName("말포이")
                .build();
        //when
        User save = userRepository.save(user);

        //then
        assertNotNull(save);
    }

    @Test
    @DisplayName("이메일로 회원 조회하기")
    void findByEmailTest() {
        //given
        String email = "abc1234@abc.com";
        //when
        Optional<User> userOptional = userRepository.findByEmail(email);
        //then
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        assertEquals("말포이",user.getUserName());

        System.out.println("\n\n\n");
        System.out.println("user.getUserName() = " + user.getUserName());
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("이메일 중복 확인하기")
    void emilDuplicateTest() {
        //given
        String email = "abc1234@abc.1234";
        boolean b = userRepository.existsByEmail(email);
        //when
        //then
    }




}