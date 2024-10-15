package org.lessons.java.ticket_platform.controller;

import java.security.Principal;

import org.lessons.java.ticket_platform.model.User;
import org.lessons.java.ticket_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private UserService uService;

    @ModelAttribute
    public void addLoggedUser(Model model, Principal principal, Authentication authentication) {
     
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            User loggedUser = uService.findByUsername(principal.getName());
            if (loggedUser != null) {
                model.addAttribute("loggedUser", loggedUser);
                model.addAttribute("loggedUserId", loggedUser.getId());
            }
        }
    }

}