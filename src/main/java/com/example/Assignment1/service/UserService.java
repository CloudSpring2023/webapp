package com.example.assignment1.service;

import com.example.assignment1.Exception.DataNotFoundException;
import com.example.assignment1.Exception.UserExistException;
import com.example.assignment1.entity.UserInfo;
import com.example.assignment1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    //Post method to save info in Database
    public UserInfo saveUser(UserInfo userinfo)throws UserExistException {

        UserInfo userObj= repository.findByEmailID(userinfo.getEmailID());
        if(userObj!=null){
            throw new UserExistException("User Already Exists");
        }
        userinfo.setId(userinfo.getId());
        userinfo.setFName(userinfo.getFName());
        userinfo.setLName(userinfo.getLName());
        userinfo.setEmailID(userinfo.getEmailID());
        userinfo.setPassword(new BCryptPasswordEncoder().encode(userinfo.getPassword()));
        System.out.println(userinfo);
        return repository.save(userinfo);
    }
    public List<UserInfo> saveUsers(List<UserInfo> usersinfo){

        return repository.saveAll(usersinfo);
    }

    //To get the User info from Database
    public List<UserInfo> getUsers(){
        return repository.findAll();
    }
    //Get user info by ID
    public UserInfo getUserById(int id) throws DataNotFoundException {

        UserInfo userInfo = repository.findById(id).orElse(null);
        if(userInfo!=null){
            return userInfo;
        }
        throw new DataNotFoundException("Data Not Found");

    }
    /*public UserInfo getUserByEmailId(String name) {
        return repository.findByEmailID(name);
    }*/

    /*public UserInfo getUserByEmail(String email){
        return repository.findByEmail(email);
    }*/
//method for deleting the User
    public String deleteUser(int id){
        repository.deleteById(id);
        return "User Removed!!" + id;
    }

    //Method to update

    public UserInfo updateUserInfo(UserInfo userInfo){
        UserInfo existingUserInfo = repository.findById(userInfo.getId()).orElse(null);
        existingUserInfo.setFName(userInfo.getFName());
        existingUserInfo.setLName(userInfo.getLName());
        //  userinfo.setPassword(new BCryptPasswordEncoder().encode(userinfo.getPassword()));
        existingUserInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
        return repository.save(existingUserInfo);

    }
}
