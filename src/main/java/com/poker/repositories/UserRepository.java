package com.poker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poker.entities.User;


public interface UserRepository extends JpaRepository<User, Long>{

}
