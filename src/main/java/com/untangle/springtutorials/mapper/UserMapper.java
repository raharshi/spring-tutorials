package com.untangle.springtutorials.mapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.untangle.springtutorials.dto.User;
import com.untangle.springtutorials.model.UserCredEntity;
import com.untangle.springtutorials.model.UserEntity;

public class UserMapper {

    public static UserEntity maptoUserEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setUserName(user.getUserName());
        entity.setGender(user.getGender());
        entity.setAge(user.getAge());
        entity.setRoles(user.getRoles().stream().collect(Collectors.joining(",")));
        return entity;
    }

    public static User maptoUser(UserEntity entity){
        User user = new User();
        user.setId(entity.getId());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setUserName(entity.getUserName());
        user.setGender(entity.getGender());
        user.setAge(entity.getAge());
        user.setRoles(Arrays.stream(entity.getRoles().split(",")).map(String::new).collect(Collectors.toList()));
        return user;
    }

    public static UserCredEntity mapToCredEntity(User user){
        UserCredEntity cred =  new UserCredEntity();
        cred.setUsername(user.getUserName());
        cred.setPassword(user.getPassword());
        return cred;
    }
    
    public static User mapCredToUser(UserCredEntity cred){
        User user = new User();
        user.setPassword(cred.getPassword());
        user.setUserName(cred.getUsername());
        return user;
    }

}
