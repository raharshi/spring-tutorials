package com.untangle.springtutorials.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.untangle.springtutorials.dto.User;
import com.untangle.springtutorials.dto.UserInfo;
import com.untangle.springtutorials.mapper.UserMapper;
import com.untangle.springtutorials.model.UserCredEntity;
import com.untangle.springtutorials.model.UserEntity;
import com.untangle.springtutorials.repository.UserCredRepository;
import com.untangle.springtutorials.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserCredRepository credRepo;

    @Autowired
    private PasswordEncoder encoder;

    @SuppressWarnings("null")
    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        UserEntity entity = userRepo.save(UserMapper.maptoUserEntity(user));
        UserCredEntity credEntity = UserMapper.mapToCredEntity(user);
        credEntity.setUser(entity);
        credRepo.save(credEntity);
        user.setId(entity.getId());
        return user;
    }

    public User getUser(long id) {
        Optional<UserEntity> userOp = userRepo.findById(id);
        if(userOp.isPresent()){
            return UserMapper.maptoUser(userRepo.findById(id).get());
        }
        return new User();
    }

    public List<User> getAllUsers() {
        return userRepo.findAll().stream().map(e -> UserMapper.maptoUser(e)).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredEntity> user = credRepo.findByUsername(username);
        return user.map(UserInfo::new).orElseThrow(()-> new UsernameNotFoundException("User not found with username "+username));
    }
}
