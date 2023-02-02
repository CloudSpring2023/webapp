package com.example.assignment1.controller;

import com.example.assignment1.Exception.*;
import com.example.assignment1.constants.UserContants;
import com.example.assignment1.entity.UserInfo;
import com.example.assignment1.entity.UserUpdateRequestModel;
import com.example.assignment1.service.AuthService;
import com.example.assignment1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1")
public class UserInfoController {
    @Autowired
    private UserService service;
    @Autowired
    AuthService authService;
    //Post Method input json can be parsed to the UserInfo Object
    @PostMapping("/user")
    public ResponseEntity<String> createUser(@Validated @RequestBody UserInfo user, Errors error){
        try {
            if(error.hasErrors()) {
                String response = error.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining(","));
                throw new InvalidUserInputException(response);
            }
            return new ResponseEntity<String>( service.createUser(user),HttpStatus.CREATED);
        } catch (InvalidUserInputException e) {
            // TODO Auto-generated catch block
            return new ResponseEntity<String>( e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (UserExistException e) {
            // TODO Auto-generated catch block
            return new ResponseEntity<String>( e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch(Exception e) {
            return new ResponseEntity<String>(UserContants.InternalErr,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //UserInfo addUser(@RequestBody UserInfo newUser) {return service.saveUser(newUser);}
    //Get API with UserId
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<?> getUserDetails(@PathVariable("userId") UUID userId, HttpServletRequest request){
        try {
            if(userId.toString().isBlank()||userId.toString().isEmpty()) {
                throw new InvalidUserInputException("Enter Valid User Id");
            }
            authService.isAuthorised(userId,request.getHeader("Authorization").split(" ")[1]);
            return new ResponseEntity<com.example.assignment1.entity.UserDto>( service.getUserDetails(userId),HttpStatus.OK);
        } catch (InvalidUserInputException e) {
            // TODO Auto-generated catch block
            return new ResponseEntity<String>( e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (UserAuthrizationException e) {
            // TODO Auto-generated catch block
            return new ResponseEntity<String>( e.getMessage(),HttpStatus.FORBIDDEN);
        }
        catch (DataNotFoundException e) {
            // TODO Auto-generated catch block
            return new ResponseEntity<String>( e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            return new ResponseEntity<String>(UserContants.InternalErr,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    //Update API--PUT
    @PutMapping(value = "/user/{userId}")
    public ResponseEntity<?> updateUserDetails(@PathVariable("userId") UUID userId, @Validated @RequestBody UserUpdateRequestModel user,
                                               HttpServletRequest request, Errors error){
        try {
            if(userId.toString().isBlank()||userId.toString().isEmpty()) {
                throw new InvalidUserInputException("Enter Valid User Id");
            }
            System.out.println(user);
            authService.isAuthorised(userId,request.getHeader("Authorization").split(" ")[1]);
            if(error.hasErrors()) {
                String response = error.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining(","));
                throw new InvalidUserInputException(response);
            }
            return new ResponseEntity<String>( service.updateUserDetails(userId,user),HttpStatus.CREATED);
        } catch (InvalidUserInputException e) {
            // TODO Auto-generated catch block
            return new ResponseEntity<String>( e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (UserAuthrizationException e) {
            // TODO Auto-generated catch block
            return new ResponseEntity<String>( e.getMessage(),HttpStatus.FORBIDDEN);
        }
        catch (DataNotFoundException e) {
            // TODO Auto-generated catch block
            return new ResponseEntity<String>( e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            return new ResponseEntity<String>(UserContants.InternalErr,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

