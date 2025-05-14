package com.example.genkichat.service;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.genkichat.entity.Board;
import com.example.genkichat.entity.Text;
import com.example.genkichat.form.TextForm;
import com.example.genkichat.repository.BoardRepository;
import com.example.genkichat.repository.TextRepository;

@Service
public class TextService {

	private final TextRepository textRepository;
	private final BoardRepository boardRepository;

	public TextService(TextRepository textRepository, BoardRepository boardRepository) {
		this.textRepository = textRepository;
		this.boardRepository = boardRepository;
	}

	@Transactional
	public void createText(TextForm textForm) {
		Text newText = new Text();
		if (!textForm.getNickname().isEmpty()) {
			newText.setNickname(textForm.getNickname());
		} else {
			newText.setNickname("匿名");
		}

		newText.setText(textForm.getText());

		Board board = boardRepository.findById(textForm.getBoardId())
				.orElseThrow(() -> new IllegalArgumentException("指定されたタイトルが存在しません"));

		Timestamp now = Timestamp.from(Instant.now());
		board.setUpdatedAt(now);
		boardRepository.save(board);
		boardRepository.flush();

		newText.setBoard(board);
		textRepository.save(newText);
	}

	@Transactional
	public void deleteTextById(Integer id) {
		if (!textRepository.existsById(id)) {
			throw new IllegalArgumentException("指定された投稿が存在しません。");
		}
		textRepository.deleteById(id);
	}
}
