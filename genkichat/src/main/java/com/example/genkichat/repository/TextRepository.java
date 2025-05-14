package com.example.genkichat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.genkichat.entity.Board;
import com.example.genkichat.entity.Text;

public interface TextRepository extends JpaRepository<Text, Integer> {
	List<Text> findByBoard(Board board);

	List<Text> findTop5ByOrderByCreatedAtDesc();

	List<Text> findByBoardId(Integer boardId);

	List<Text> findByBoardIdOrderByCreatedAtDesc(Integer boardId);
}
