package com.example.assignment1.repository;

import com.example.assignment1.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<UserInfo,Long> {

    UserInfo findByUsername(String username);
}
