package com.example.assignment1.repository;

import com.example.assignment1.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Integer> {
    UserInfo findByEmailID(String name);
//    UserInfo findByEmail(String email)

}
