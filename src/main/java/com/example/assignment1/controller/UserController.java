package com.example.assignment1.controller;

import java.util.stream.Collectors;

import com.example.assignment1.Exception.DataNotFoundException;
import com.example.assignment1.Exception.InvalidUserInputException;
import com.example.assignment1.Exception.UserAuthrizationException;
import com.example.assignment1.Exception.UserExistException;
import com.example.assignment1.constants.UserConstants;
import com.example.assignment1.entity.UserDto;
import com.example.assignment1.entity.UserInfo;
import com.example.assignment1.entity.UserUpdateRequestModel;
import com.example.assignment1.service.AuthService;
import com.example.assignment1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;


    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getUserDetails(@PathVariable("userId") Long userId,HttpServletRequest request){
        try {
            if(userId.toString().isBlank()||userId.toString().isEmpty()) {
                throw new InvalidUserInputException("Enter Valid User Id");
            }
            authService.isAuthorised(userId,request.getHeader("Authorization").split(" ")[1]);
            return new ResponseEntity<UserDto>( userService.getUserDetails(userId),HttpStatus.OK);
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
            return new ResponseEntity<String>(UserConstants.InternalErr,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<?> updateUserDetails(@PathVariable("userId") Long userId,@Valid @RequestBody UserUpdateRequestModel user,
                                               HttpServletRequest request,Errors error){
        try {
            if(userId.toString().isBlank()||userId.toString().isEmpty()) {
                throw new InvalidUserInputException("Enter Valid User Id");
            }
            authService.isAuthorised(userId,request.getHeader("Authorization").split(" ")[1]);
            if(error.hasErrors()) {
                String response = error.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining(","));
                throw new InvalidUserInputException(response);
            }
            return new ResponseEntity<String>( userService.updateUserDetails(userId,user),HttpStatus.NO_CONTENT);
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
            return new ResponseEntity<String>(UserConstants.InternalErr,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping()
    public ResponseEntity<?> createUser(@Valid @RequestBody UserInfo user, Errors error){
        try {
            if(error.hasErrors()) {
                String response = error.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining(","));
                throw new InvalidUserInputException(response);
            }
            return new ResponseEntity<UserInfo>( userService.createUser(user),HttpStatus.CREATED);
        } catch (InvalidUserInputException e) {
            // TODO Auto-generated catch block
            return new ResponseEntity<String>( e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (UserExistException e) {
            // TODO Auto-generated catch block
            return new ResponseEntity<String>( e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch(Exception e) {
            return new ResponseEntity<String>(UserConstants.InternalErr,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

