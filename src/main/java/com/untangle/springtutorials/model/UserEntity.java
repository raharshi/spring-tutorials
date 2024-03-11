package com.untangle.springtutorials.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_details")
public class UserEntity {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String gender;
    private String age;
    private String roles;
    @OneToOne(mappedBy = "user")
    private UserCredEntity cred;

}
