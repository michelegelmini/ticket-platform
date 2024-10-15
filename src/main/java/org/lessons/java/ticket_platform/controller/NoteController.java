package org.lessons.java.ticket_platform.controller;

import org.lessons.java.ticket_platform.model.Note;

import org.lessons.java.ticket_platform.service.NoteService;
import org.lessons.java.ticket_platform.service.TicketService;
import org.lessons.java.ticket_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	private TicketService tService;

	@Autowired
	private NoteService nService;

	@Autowired
	private UserService uService;


	// Store
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("addedNote") Note formNote, @RequestParam("ticketId") Integer ticketId,
			@RequestParam("userId") Integer userId, BindingResult bindingResult, RedirectAttributes attributes,
			Model model) {

		if (bindingResult.hasErrors()) {

			return "/home";
		}

		formNote.setTicket(tService.findById(ticketId).get());
		formNote.setAuthor(uService.findById(userId));

		nService.create(formNote);
		attributes.addFlashAttribute("successMessage", formNote.getContent() + " has been created!");

		return "redirect:/tickets/" + formNote.getTicket().getId();
	}
}


