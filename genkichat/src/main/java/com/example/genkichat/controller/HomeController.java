package com.example.genkichat.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.genkichat.entity.Board;
import com.example.genkichat.entity.Text;
import com.example.genkichat.form.BoardForm;
import com.example.genkichat.repository.BoardRepository;
import com.example.genkichat.repository.TextRepository;
import com.example.genkichat.service.BoardService;

@Controller
public class HomeController {
	private final BoardRepository boardRepository;
	private final TextRepository textRepository;
	private BoardService boardService;

	public HomeController(BoardRepository boardRepository, TextRepository textRepository, BoardService boardService) {
		this.boardRepository = boardRepository;
		this.textRepository = textRepository;
		this.boardService = boardService;
	}

	@GetMapping("/")
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {

		Page<Board> boardPage = Page.empty(); // 空のページで初期化

		if (keyword != null && !keyword.isEmpty()) {
			boardPage = boardRepository.findByTitleLike("%" + keyword + "%", pageable);
		} else {
			boardPage = boardRepository.findAllByOrderByCreatedAtDesc(pageable);
		}

		List<Text> latestTexts = textRepository.findTop5ByOrderByCreatedAtDesc();

		model.addAttribute("boardPage", boardPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("latestTexts", latestTexts);

		return "index";
	}

	@PostMapping("/boards/create")
	public String createBoard(@ModelAttribute BoardForm boardForm) {
		Integer boardId = boardService.createBoardWithText(boardForm);
		return "redirect:/boards/" + boardId;
	}

}
