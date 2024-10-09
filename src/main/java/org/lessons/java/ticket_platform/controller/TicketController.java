package org.lessons.java.ticket_platform.controller;

import java.util.List;

import org.lessons.java.ticket_platform.model.Category;
import org.lessons.java.ticket_platform.model.Ticket;
import org.lessons.java.ticket_platform.service.CategoryService;
import org.lessons.java.ticket_platform.service.TicketService;
import org.lessons.java.ticket_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {
	
	@Autowired
	private TicketService tService;
	
	@Autowired
	private CategoryService cService;
	
	@Autowired
	private UserService uService;
	
	
	
	@GetMapping
	public String index(Model model, @RequestParam(name = "title", required = false) String title, @RequestParam(name = "category", required = false) Category category, @RequestParam(name = "status", required = false) String status) {

		model.addAttribute("ticketName", title);
		model.addAttribute("categories", cService.findAllSortedById());
		//model.addAttribute("username", authentication.getName());
		
		List<Ticket> ticketList;

		if (title != null && !title.isEmpty()) {
			model.addAttribute("ticketTitle", title);
			ticketList = tService.findByTitle(title);

		} else if (category != null) {
			model.addAttribute("category", category);
			ticketList = tService.findByCategory(category.getName());
			
		} else if (status != null && !status.isEmpty()) {
			model.addAttribute("status", status);
			ticketList = tService.findByStatus(status);
		} else {
			// prendo i dati da consegnare a tickets
			ticketList = tService.findAllSortedById();
		}

		// li inserisco nel modello
		model.addAttribute("tickets", ticketList);

		return "/tickets/index";
	}
	
	//show
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer ticketId, Model model) {
		model.addAttribute("ticket", tService.findById(ticketId));
		return "/tickets/show";
	}
	
	//create	
	@GetMapping("/create")
	public String create(Model model) {
		Ticket newTicket = new Ticket();
		newTicket.setStatus("To do");
		model.addAttribute("ticket", newTicket);
		model.addAttribute("categories", cService.findAllSortedById());
		model.addAttribute("users", uService.findAllSortedById());

		return "/tickets/create";
	}
	
	//Store
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, RedirectAttributes attributes, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", cService.findAllSortedById());
			model.addAttribute("users", uService.findAllSortedById());
		
			return "/tickets/create";
		}
		formTicket.setStatus("To do");
		tService.create(formTicket);
		attributes.addFlashAttribute("successMessage", formTicket.getTitle() + " has been created!");
		
		
		return "redirect:/tickets";
	}
	
	//edit
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		

		model.addAttribute("ticket", tService.findById(id));
		model.addAttribute("categories", cService.findAllSortedById());
		model.addAttribute("users", uService.findAllSortedById());
		
		
		//restituisco la view con il model inserito
		return "tickets/edit";
	}
	
	//update
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket updatedFormTicket, BindingResult bindingResult, RedirectAttributes attributes, Model model) {
		
		//se ci sono errori nel form, mostra gli errori
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", cService.findAllSortedById());
			model.addAttribute("users", uService.findAllSortedById());
	
			return "/tickets/edit";
		}
		//altrimenti salva il ticket
		tService.update(updatedFormTicket);	

		attributes.addFlashAttribute("successMessage", "Ticket with id " + updatedFormTicket.getId() + ": " +updatedFormTicket.getTitle() + ", has been UPDATED!");
		
		
		return "redirect:/tickets";
	}
	
	//delete
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		
		//deleteById cerca ed elimina in un unico comando
		
		Ticket ticketToDelete = tService.findById(id);
		tService.delete(id);
		
		attributes.addFlashAttribute("deletedMessage", "Ticket with id " + id +": " + ticketToDelete.getTitle() + ", has been DELETED!");
		
		return "redirect:/tickets";
	}
	
	

}
