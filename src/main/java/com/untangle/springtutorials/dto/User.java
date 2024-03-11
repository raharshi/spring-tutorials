package com.untangle.springtutorials.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String gender;
    private String age;
    private String password;
    private String confirmPassword;
    private List<String> roles;

    public User(){
        roles = new ArrayList<String>();
    }


}
