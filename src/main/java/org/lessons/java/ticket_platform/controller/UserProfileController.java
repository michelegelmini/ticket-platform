package org.lessons.java.ticket_platform.controller;

import java.security.Principal;


import org.lessons.java.ticket_platform.model.User;
import org.lessons.java.ticket_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class UserProfileController {
	@Autowired
    private UserService userService; 

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        String username = principal.getName(); 
        
        User user = userService.findByUsername(username);
      
        model.addAttribute("user", user);
        model.addAttribute("tickets", user.getTickets()); 
        
        
        return "users/profile"; 
    }

}
