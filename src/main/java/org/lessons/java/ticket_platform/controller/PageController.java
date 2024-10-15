package org.lessons.java.ticket_platform.controller;

import java.security.Principal;
import java.util.List;

import org.lessons.java.ticket_platform.model.User;
import org.lessons.java.ticket_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PageController {
	
	@Autowired
	private UserService uService;

	@GetMapping("*")
	public String homepage(Model model, @RequestParam(name = "username", required = false) String username,
			Principal principal, Authentication authentication) {

		if (principal == null) {
			return "redirect:/login";
		}
		
		
//		String loggedUser = principal.getName();
		List<User> userList;
		User loggedUser = uService.findByUsername(principal.getName());
		model.addAttribute("loggedUser", loggedUser);
		int loggedUserId = loggedUser.getId();
		model.addAttribute("loggedUserId", loggedUserId);
	
		return "pages/home";	
	}

	@PostMapping("/pages/login")
	public String homepage() {
		return "pages/home";
	}

	@GetMapping("/pages/error")
	public String error() {
		return "/pages/error";
	}

}


