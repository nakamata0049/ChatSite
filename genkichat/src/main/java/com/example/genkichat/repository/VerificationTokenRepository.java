package com.example.genkichat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.genkichat.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
	VerificationToken findByToken(String token);
}
