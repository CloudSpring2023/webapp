package com.example.assignment1.service;

import com.example.assignment1.Exception.DataNotFoundException;
import com.example.assignment1.Exception.UserAuthrizationException;
import com.example.assignment1.entity.UserInfo;
import com.example.assignment1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    UserRepository repository;

    public BCryptPasswordEncoder PassEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserInfo getUserDetailsAuth(Long userId) throws DataNotFoundException {
        Optional<UserInfo> user = repository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new DataNotFoundException("User Not Found");
    }

    public boolean isAuthorised(Long userId,String tokenEnc) throws DataNotFoundException, UserAuthrizationException {

        UserInfo user=getUserDetailsAuth(userId);
        byte[] token = Base64.getDecoder().decode(tokenEnc);
        String decodedStr = new String(token, StandardCharsets.UTF_8);

        String userName = decodedStr.split(":")[0];
        String passWord = decodedStr.split(":")[1];
        System.out.println("Value of Token" + " "+ decodedStr);
        if(!((user.getUsername().equals(userName)) && (PassEncoder().matches(passWord,user.getPassword())))){
            throw new UserAuthrizationException("Forbidden to access");
        }
        return true;
    }

    public String getUserNameFromToken(String tokenEnc) {
        byte[] token = Base64.getDecoder().decode(tokenEnc);
        String decodedStr = new String(token, StandardCharsets.UTF_8);
        String userName = decodedStr.split(":")[0];
        String passWord = decodedStr.split(":")[1];
//        System.out.println("Value of Token" + " "+ decodedStr);
        return userName;
    }
}
