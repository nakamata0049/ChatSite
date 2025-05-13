package com.example.genkichat.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.genkichat.entity.Board;
import com.example.genkichat.entity.Text;
import com.example.genkichat.form.TextForm;
import com.example.genkichat.repository.BoardRepository;
import com.example.genkichat.repository.TextRepository;

@Controller
@RequestMapping("/boards")
public class BoardController {
	private final TextRepository textRepository;
	private final BoardRepository boardRepository;

	public BoardController(TextRepository textRepository, BoardRepository boardRepository) {
		this.textRepository = textRepository;
		this.boardRepository = boardRepository;
	}

	@GetMapping("/{board}")
	public String show(@PathVariable(name = "board") Board board, Model model) {
		List<Text> text = textRepository.findByBoard(board);

		model.addAttribute("text", text);

		return "/show";
	}

	@PostMapping("/createText")
	@Transactional
	public String createText(@ModelAttribute TextForm textForm) {
		Text newText = new Text();
		newText.setNickname(textForm.getNickname());
		newText.setText(textForm.getText());

		//titleId から BoardTitle を取得して設定
		Board board = boardRepository.findById(textForm.getTitleId())
				.orElseThrow(() -> new IllegalArgumentException("指定されたタイトルが存在しません"));

		// BoardTitleのupdatedAtを現在時刻で更新
		//title.setUpdatedAt(Timestamp.from(Instant.now()));
		//boardTitleRepository.save(title); // 明示的に更新を反映
		
		
		// BoardTitleの updatedAt を更新
	    Timestamp now = Timestamp.from(Instant.now());
	    board.setUpdatedAt(now);  // updatedAt の設定
	    System.out.println("Updated Title UpdatedAt (before save): " + board.getUpdatedAt());

	    // 保存し、flush して反映を確認
	    boardRepository.save(board);
	    boardRepository.flush(); // 明示的に flush
	    
	 // 保存後に再確認
	    System.out.println("Updated Title UpdatedAt (after save): " + board.getUpdatedAt());
		
		

		newText.setBoard(board);
		textRepository.save(newText);

		return "redirect:/boards/" + textForm.getTitleId();
	}
}
