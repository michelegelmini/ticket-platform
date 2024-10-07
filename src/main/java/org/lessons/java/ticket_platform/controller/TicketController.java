package org.lessons.java.ticket_platform.controller;

import java.util.List;

import org.lessons.java.ticket_platform.model.Ticket;
import org.lessons.java.ticket_platform.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tickets")
public class TicketController {
	
	@Autowired
	private TicketService tService;
	
	@GetMapping
	public String index(Model model, @RequestParam(name = "title", required = false) String title) {

		model.addAttribute("ticketName", title);
		//model.addAttribute("username", authentication.getName());
		
		List<Ticket> ticketList;

		if (title != null && !title.isEmpty()) {
			model.addAttribute("pizzaName", title);
			ticketList = tService.findByTitle(title);

		} else {
			// prendo i dati da consegnare a pizzas
			ticketList = tService.findAllSortedById();
		}

		// pizzaList = repo.findAll();
		// li inserisco nel modello
		model.addAttribute("tickets", ticketList);

		return "/tickets/index";
	}

}
