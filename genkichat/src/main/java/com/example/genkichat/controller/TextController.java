package com.example.genkichat.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.genkichat.entity.Board;
import com.example.genkichat.entity.Text;
import com.example.genkichat.form.TextForm;
import com.example.genkichat.repository.BoardRepository;
import com.example.genkichat.repository.TextRepository;
import com.example.genkichat.service.BoardService;
import com.example.genkichat.service.TextService;

@Controller
@RequestMapping("/boards")
public class TextController {
	private final TextRepository textRepository;
	private final TextService textService;
	private final BoardService boardService;
	private final BoardRepository boardRepository;

	public TextController(TextRepository textRepository, TextService textService, BoardService boardService,
			BoardRepository boardRepository) {
		this.textRepository = textRepository;
		this.textService = textService;
		this.boardService = boardService;
		this.boardRepository = boardRepository;
	}

	@GetMapping("/{boardId}")
	public String show(@PathVariable Integer boardId, Model model) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new IllegalArgumentException("指定された掲示板が見つかりませんでした"));
		List<Text> text = textRepository.findByBoardIdOrderByCreatedAtDesc(boardId);

		List<Text> latestTexts = textRepository.findTop5ByOrderByCreatedAtDesc();

		model.addAttribute("board", board);
		model.addAttribute("texts", text);
		model.addAttribute("latestTexts", latestTexts);

		return "/show";
	}

	@PostMapping("/createText")
	public String createText(@ModelAttribute TextForm textForm) {
		textService.createText(textForm);

		return "redirect:/boards/" + textForm.getBoardId();
	}

	@PostMapping("/deleteText")
	public String deleteText(@RequestParam Integer textId, @RequestParam Integer boardId,
			RedirectAttributes redirectAttributes) {
		textService.deleteTextById(textId);
		redirectAttributes.addFlashAttribute("message", "投稿を削除しました。");
		return "redirect:/boards/" + boardId;
	}

	@PostMapping("/deleteBoardWithText")
	public String deleteBoardWithText(@RequestParam Integer textId, @RequestParam Integer boardId,
			RedirectAttributes redirectAttributes) {
		textService.deleteTextById(textId);
		boardService.deleteBoardById(boardId);
		redirectAttributes.addFlashAttribute("message", "削除しました。");
		return "redirect:/";
	}

	@PostMapping("/deleteBoard")
	public String deleteBoard(@RequestParam Integer boardId, RedirectAttributes redirectAttributes) {
		boardService.deleteBoardById(boardId);
		redirectAttributes.addFlashAttribute("message", "削除しました。");
		return "redirect:/";
	}

}
