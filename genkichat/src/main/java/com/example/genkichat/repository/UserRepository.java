package com.example.genkichat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.genkichat.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
