package org.lessons.java.ticket_platform.controller;

import java.security.Principal;

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


	@GetMapping("*")
	public String homepage(Model model, @RequestParam(name = "username", required = false) String username,
			Principal principal, Authentication authentication) {

		if (principal == null) {
			return "redirect:/login";
		}
		
		return "pages/home";	
	}

	@PostMapping("/pages/login")
	public String homepage() {
		return "pages/home";
	}

	@GetMapping("/pages/accessDenied")
	public String error() {
		return "/pages/accessDenied";
	}

}


