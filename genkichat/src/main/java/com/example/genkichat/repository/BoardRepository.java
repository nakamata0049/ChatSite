package com.example.genkichat.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.genkichat.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	public Page<Board> findByTitleLike(String keyword, Pageable pageable);
	
	public Page<Board> findByOrderByCreatedAtDesc(Pageable pageable);

	public Optional<Board> findByTitle(Board board);

	public Page<Board> findAllByOrderByUpdatedAtDesc(Pageable pageable);

	public Page<Board> findAllByOrderByCreatedAtDesc(Pageable pageable);
	
}
