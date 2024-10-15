package org.lessons.java.ticket_platform.controller;

import java.security.Principal;
import java.util.List;

import org.lessons.java.ticket_platform.model.Category;
import org.lessons.java.ticket_platform.model.Ticket;
import org.lessons.java.ticket_platform.model.User;
import org.lessons.java.ticket_platform.service.CategoryService;
import org.lessons.java.ticket_platform.service.NoteService;
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
	
	@Autowired
	private NoteService nService;

	
	//Index
	@GetMapping
	public String index(Model model, @RequestParam(name = "title", required = false) String title,
			@RequestParam(name = "category", required = false) Category category,
			@RequestParam(name = "status", required = false) String status,
			Authentication authentication,
			Principal principal) {

		List<Ticket> ticketList;
		User loggedUser = uService.findByUsername(principal.getName());
		int loggedUserId = loggedUser.getId();

		model.addAttribute("ticketTitle", title);
		model.addAttribute("categories", cService.findAllSortedById());
		model.addAttribute("username", authentication.getName());

		if (loggedUser.isAdmin()) {
			//filter by title
			if (title != null && !title.isEmpty()) {
				model.addAttribute("ticketTitle", title);
				ticketList = tService.findByTitle(title);
				//filter by category
			} else if (category != null) {
				model.addAttribute("category", category);
				ticketList = tService.findByCategory(category.getName());
				//filter by status
			} else if (status != null && !status.isEmpty()) {
				model.addAttribute("status", status);
				ticketList = tService.findByStatus(status);
			} else {
				// no filter
				ticketList = tService.findAllSortedById();
			}

		} else {

			ticketList = tService.findByUser(loggedUserId);

			if (title != null && !title.isEmpty()) {
				model.addAttribute("ticketTitle", title);
				ticketList = tService.findByUserAndTitle(loggedUserId, title);
			}
			// filter by category
			else if (category != null) {
				model.addAttribute("category", category);
				ticketList = tService.findByUserAndCategory(loggedUserId, category.getName());
				//filter by status
			} else if (status != null && !status.isEmpty()) {
				model.addAttribute("status", status);
				ticketList = tService.findByUserAndStatus(loggedUserId, status);
			}
		}


		model.addAttribute("tickets", ticketList);
		model.addAttribute("loggedUserId", loggedUserId);
		return "/tickets/index";

	}

	// show
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer ticketId, Model model, Principal principal, Authentication authentication) {
		
		Ticket ticket = tService.findById(ticketId).get();
		User loggedUser = uService.findByUsername(principal.getName());
		int loggedUserId = loggedUser.getId();
		
		
		model.addAttribute("notes", nService.findByTicketId(ticketId));
		model.addAttribute("ticket", tService.findSelectedById(ticketId));
		model.addAttribute("users", uService.findAllSortedById());
		model.addAttribute("categories", cService.findAllSortedById());
		model.addAttribute("loggedUserId", loggedUserId);		
		
		if (loggedUser.isAdmin()) {
		    return "/tickets/show"; 
		}
		
		
		if (ticket.getUser() == null) {
		    return "/pages/accessDenied"; 
		} else if (!ticket.getUser().getId().equals(loggedUserId)) {
		    return "/pages/accessDenied"; 
		}
	
		return "/tickets/show";
	}

	// create
	@GetMapping("/create")
	public String create(Model model) {
		Ticket newTicket = new Ticket();
		newTicket.setDoingStatus();
		model.addAttribute("ticket", newTicket);
		model.addAttribute("categories", cService.findAllSortedById());
		model.addAttribute("users", uService.findAllSortedById());

		return "/tickets/create";
	}

	// Store
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult,
			RedirectAttributes attributes, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", cService.findAllSortedById());
			model.addAttribute("users", uService.findAllSortedById());

			return "/tickets/create";
		}
		formTicket.setDoingStatus();
		tService.create(formTicket);
		attributes.addFlashAttribute("successMessage", formTicket.getTitle() + " has been created!");

		return "redirect:/tickets";
	}

	// edit
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("ticket", tService.findSelectedById(id));
		model.addAttribute("categories", cService.findAllSortedById());
		model.addAttribute("users", uService.findAllSortedById());

		return "tickets/edit";
	}

	// update
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket updatedFormTicket, BindingResult bindingResult,
			RedirectAttributes attributes, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", cService.findAllSortedById());
			model.addAttribute("users", uService.findAllSortedById());

			return "/tickets/edit";
		}

		updatedFormTicket.setDoingStatus();
		tService.update(updatedFormTicket);

		attributes.addFlashAttribute("successMessage", "Ticket with id " + updatedFormTicket.getId() + ": "
				+ updatedFormTicket.getTitle() + ", has been UPDATED!");

		return "redirect:/tickets/" + updatedFormTicket.getId();
	}

	// delete
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes, Principal principal,
			Authentication authentication) {

		if (principal.getName().equals("admin")) {
			Ticket ticketToDelete = tService.findById(id).get();
			tService.delete(id);

			attributes.addFlashAttribute("deletedMessage",
					"Ticket with id " + id + ": " + ticketToDelete.getTitle() + ", has been DELETED!");
		}
		return "redirect:/tickets";
	}

	// set to done
	@PostMapping("/done/{id}")
	public String done(@PathVariable("id") Integer id, RedirectAttributes attributes) {

		Ticket ticketToSetDone = tService.findById(id).get();
		ticketToSetDone.setStatus("Done");
		tService.update(ticketToSetDone);

		attributes.addFlashAttribute("successMessage",
				"Ticket with id " + id + ": " + ticketToSetDone.getTitle() + ", has been DONE!");
		return "redirect:/tickets";

	}
	
	
	
	

}
