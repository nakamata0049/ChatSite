package com.example.genkichat.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.genkichat.entity.Text;
import com.example.genkichat.form.TextForm;
import com.example.genkichat.repository.TextRepository;

@Controller
@RequestMapping("/boards")
public class BoardController {
	private final TextRepository textRepository;

	public BoardController(TextRepository textRepository) {
		this.textRepository = textRepository;
	}

	@GetMapping("/{title_id}")
	public String show(@PathVariable(name = "title_id") Integer title_id, Model model) {
		List<Text> text = textRepository.findByTitleId(title_id);

		model.addAttribute("text", text);

		return "/show";
	}
	
	@PostMapping("/submitPost")
	public String submitPost(@ModelAttribute TextForm textForm) {
		Text newText = new Text();
		newText.setNickname(textForm.getNickname());
		newText.setText(textForm.getText());
		
		textRepository.save(newText);
		
		return "redirect:/show";
	}
}
