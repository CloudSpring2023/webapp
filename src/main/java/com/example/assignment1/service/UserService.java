package com.example.assignment1.service;
import java.util.Optional;
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
@Service
public class UserService {

    @Autowired
    UserRepository userrepo;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public UserDto createUser(UserInfo user) throws UserExistException {
        UserInfo userDto = userrepo.findByUsername(user.getUsername());
        if (userDto == null) {
            user.setPassword(encoder().encode(user.getPassword()));
            userrepo.save(user);
            UserDto dto=UserDto.getUserDto(user);
            return dto;
        }
        throw new UserExistException("User Exists Already");
    }

    public UserDto getUserDetails(Long userId) throws DataNotFoundException {
        Optional<UserInfo> user = userrepo.findById(userId);
        if (user.isPresent()) {
            UserDto dto = UserDto.getUserDto(user.get());
            return dto;
        }
        throw new DataNotFoundException("User Not Found");
    }

    public String updateUserDetails(Long userId, UserUpdateRequestModel user) throws DataNotFoundException, UserAuthrizationException {
        Optional<UserInfo> userObj = userrepo.findById(userId);
        if (userObj.isPresent()) {
            if(!userObj.get().getUsername().equals(user.getUsername()))
                throw new UserAuthrizationException("Forbidden to Update Data");
            UserInfo dto = userObj.get();
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setPassword(encoder().encode(user.getPassword()));
            dto.setUsername(user.getUsername());
            userrepo.save(dto);
            return "Updated User Details Successfully";

        }
        throw new DataNotFoundException("User Not Found");
    }

    public UserInfo loadUserByUsername(String username) {
        // TODO Auto-generated method stub
        UserInfo user = userrepo.findByUsername(username);
        if (user == null) {
            return null;
        }
        return user;
    }

}
