package com.example.genkichat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.genkichat.entity.BoardTitle;

public interface BoardTitleRepository extends JpaRepository<BoardTitle, Integer> {
	public Page<BoardTitle> findByTitleLike(String keyword, Pageable pageable);
	
	public Page<BoardTitle> findByOrderByCreatedAtDesc(Pageable pageable);
	
}
