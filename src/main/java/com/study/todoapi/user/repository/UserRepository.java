package com.study.todoapi.user.repository;


import com.study.todoapi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {



    // 쿼리 메서드
    // 이메일로 회원정보 조회
//    @Query("SELECT u FROM User u WHERE u.email=?1")
    Optional<User> findByEmail(String email);

    // 이메일 중복체크
//    @Query("SELECT COUNT(*) FROM User u WHERE u.email=?1")
    boolean existsByEmail(String email);
}
