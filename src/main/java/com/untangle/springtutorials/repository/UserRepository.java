package com.untangle.springtutorials.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.untangle.springtutorials.model.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>{

    Optional<UserEntity> findByUserName(String userName);

} 
