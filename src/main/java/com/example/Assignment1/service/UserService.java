package com.example.assignment1.service;

import com.example.assignment1.Exception.DataNotFoundException;
import com.example.assignment1.Exception.UserAuthrizationException;
import com.example.assignment1.Exception.UserExistException;
import com.example.assignment1.entity.UserDto;
import com.example.assignment1.entity.UserInfo;
import com.example.assignment1.entity.UserUpdateRequestModel;
import com.example.assignment1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    //Post method to save info in Database
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    public String createUser(UserInfo user) throws UserExistException {
        UserInfo userDto = repository.findByUsername(user.getUsername());
        if (userDto == null) {
            user.setPassword(encoder().encode(user.getPassword()));
            repository.save(user);
            return "Created User";
        }
        throw new UserExistException("User Exists Already");
    }

    //To get the User info from Database
    public UserDto getUserDetails(UUID userId) throws DataNotFoundException {
        Optional<UserInfo> user = repository.findById(userId);
        if (user.isPresent()) {
            UserDto dto = UserDto.getUserDto(user.get());
            return dto;
        }
        throw new DataNotFoundException("User Not Found");
    }
    //Get user info by ID
    public String updateUserDetails(UUID userId, UserUpdateRequestModel user) throws DataNotFoundException, UserAuthrizationException {
        Optional<UserInfo> userObj = repository.findById(userId);
        if (userObj.isPresent()) {
            if(!userObj.get().getUsername().equals(user.getUsername()))
                throw new UserAuthrizationException("Forbidden to Update Data");
            UserInfo dto = userObj.get();
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setPassword(encoder().encode(user.getPassword()));
            dto.setUsername(user.getUsername());
            repository.save(dto);
            return "Updated User Details Successfully";

        }
        throw new DataNotFoundException("User Not Found");
    }
    /*public UserInfo getUserByEmailId(String name) {
        return repository.findByEmailID(name);
    }*/

    /*public UserInfo getUserByEmail(String email){
        return repository.findByEmail(email);
    }*/
//method for deleting the User
    //Method to update

    public UserInfo loadUserByUsername(String username) {
        // TODO Auto-generated method stub
        UserInfo user = repository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return user;
    }
}
