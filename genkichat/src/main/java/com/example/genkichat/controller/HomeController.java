package com.example.genkichat.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.genkichat.entity.BoardTitle;
import com.example.genkichat.repository.BoardTitleRepository;

@Controller
public class HomeController {
	private final BoardTitleRepository boardTitleRepository;

	public HomeController(BoardTitleRepository boardTitleRepository) {
		this.boardTitleRepository = boardTitleRepository;
	}

	@GetMapping("/")
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {
		
		 // ログを追加して、pageableが正しく設定されているか確認
	    System.out.println("Pageable: " + pageable);
		
		Page<BoardTitle> boardTitlePage = Page.empty();  // 空のページで初期化

		if (keyword != null && !keyword.isEmpty()) {
			boardTitlePage = boardTitleRepository.findByTitleLike("%" + keyword + "%", pageable);
		} else {
			boardTitlePage = boardTitleRepository.findAll(pageable);
		}

		model.addAttribute("boardTitlePage", boardTitlePage);
		model.addAttribute("keyword", keyword);

		return "index";
	}
}
