package com.example.assignment1.controller;

import com.example.assignment1.Exception.DataNotFoundException;
import com.example.assignment1.Exception.InvalidUserInputException;
import com.example.assignment1.Exception.InvaliduserInput;
import com.example.assignment1.Exception.UserExistException;
import com.example.assignment1.entity.UserInfo;
import com.example.assignment1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class UserInfoController {
    @Autowired
    private UserService service;
    //Post Method input json can be parsed to the UserInfo Object
    @PostMapping("/v1/user")

    public ResponseEntity<?> addUser(@Validated @RequestBody UserInfo newUser, Errors error) {
        System.out.println(newUser);
        try {
            if (error.hasErrors()) {
                String response = error.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
                throw new InvalidUserInputException(response);
            }
            return new ResponseEntity<>(service.saveUser(newUser), HttpStatus.CREATED);
        } catch (InvalidUserInputException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //UserInfo addUser(@RequestBody UserInfo newUser) {return service.saveUser(newUser);}
    //Get API with UserId
    @GetMapping("/v1/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        try {
            if(id == 0){
                throw new InvaliduserInput("Enter Valid Input");
            }
            return new ResponseEntity(service.getUserById(id), HttpStatus.OK);
        }
        catch(DataNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(InvaliduserInput e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Update API--PUT
    @PutMapping("/v1/user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserInfo newUser, @PathVariable int id) throws DataNotFoundException{

        try {
            UserInfo existingUserInfo = service.getUserById(id);//Select the user mentioned based on the Id Field
            existingUserInfo.setFName(newUser.getFName());
            existingUserInfo.setLName(newUser.getLName());
           // existingUserInfo.setPassword(newUser.getPassword());
            //existingUserInfo.setEmailID(userInfo.getEmailID());
            return new ResponseEntity<>(service.saveUser(existingUserInfo), HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        return service.saveUser(userInfo);
    }

}

