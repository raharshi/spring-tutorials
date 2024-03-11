package com.untangle.springtutorials.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.untangle.springtutorials.model.UserCredEntity;
import java.util.Optional;


@Repository
public interface UserCredRepository extends JpaRepository<UserCredEntity, Long>{

    Optional<UserCredEntity> findByUsername(String username);

}
