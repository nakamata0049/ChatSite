package com.example.genkichat.service;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.genkichat.entity.Board;
import com.example.genkichat.entity.Text;
import com.example.genkichat.form.BoardForm;
import com.example.genkichat.repository.BoardRepository;
import com.example.genkichat.repository.TextRepository;

@Service
public class BoardService {
	private final BoardRepository boardRepository;
	private final TextRepository textRepository;

	public BoardService(BoardRepository boardRepository, TextRepository textRepository) {
		this.boardRepository = boardRepository;
		this.textRepository = textRepository;
	}

	@Transactional
	public Integer createBoardWithText(BoardForm boardForm) {
		Timestamp now = Timestamp.from(Instant.now());
		// Boardの保存
		Board board = new Board();
		board.setTitle(boardForm.getTitle());
		board.setUpdatedAt(now);
		board = boardRepository.save(board);

		// Textの保存
		Text text = new Text();
		if (!boardForm.getNickname().isEmpty()) {
			text.setNickname(boardForm.getNickname());
		} else {
			text.setNickname("匿名");
		}

		text.setText(boardForm.getText());
		text.setCreatedAt(now);
		text.setBoard(board); // 外部キーとしてBoardをセット
		textRepository.save(text);

		return board.getId();
	}

	public void deleteBoardById(Integer boardId) {
		boardRepository.deleteById(boardId);

	}

}
