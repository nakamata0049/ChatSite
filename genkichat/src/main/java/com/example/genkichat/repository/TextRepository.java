package com.example.genkichat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.genkichat.entity.Text;

public interface TextRepository extends JpaRepository<Text, Integer> {
	List<Text> findByTitleId(Integer titleId);
}
