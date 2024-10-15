package org.lessons.java.ticket_platform.controller;

import java.security.Principal;
import java.util.List;

import org.lessons.java.ticket_platform.model.Note;
import org.lessons.java.ticket_platform.model.Ticket;
import org.lessons.java.ticket_platform.model.User;
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
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService uService;

	@Autowired
	private TicketService tService;

	// Index
	@GetMapping
	public String index(Model model, @RequestParam(name = "username", required = false) String username,
			Principal principal, Authentication authentication) {

		List<User> userList;
		User loggedUser = uService.findByUsername(principal.getName());

		if (loggedUser.isAdmin()) {
			model.addAttribute("username", username);
			model.addAttribute("username", authentication.getName());
		} else {
			username = loggedUser.getUsername();
		}

		if (username != null && !username.isEmpty()) {
			model.addAttribute("userUsername", username);
			userList = uService.searchByUsername(username);

		} else {
			userList = uService.findAllSortedById();
		}

		model.addAttribute("tickets", tService.findAllSortedById());
		model.addAttribute("ticketsInProgress", tService.findByStatus("Doing"));
		model.addAttribute("users", userList);

		username = loggedUser.getUsername();
		if (loggedUser.isAdmin()) {
			return "/users/index";
		} else {
			return "redirect:/users/" + loggedUser.getId();
		}
	}

	// show
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer userId, Model model, Principal principal,
			Authentication authentication) {
		model.addAttribute("user", uService.findById(userId));
		model.addAttribute("tickets", tService.findByUser(userId));

		return "/users/show";
	}

	// create
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("user", new User());

		return "/users/create";
	}

	// Store
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("user") User formUser, BindingResult bindingResult,
			RedirectAttributes attributes, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("users", uService.findAllSortedById());
			return "/users/create";
		}
		uService.create(formUser);
		attributes.addFlashAttribute("successMessage", formUser.getUsername() + " has been created!");

		return "redirect:/users";
	}

	// edit
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("user", uService.findById(id));
		model.addAttribute("tickets", tService.findAllSortedById());
		model.addAttribute("oldPassword", "");

		return "users/edit";
	}

	// update
	@PostMapping("/edit/{id}")
	public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("user") User updatedFormUser,
			BindingResult bindingResult, Ticket ticket, RedirectAttributes attributes, Model model,
			Authentication authentication, Principal principal) {

		User loggedUser = uService.findByUsername(principal.getName());
		User existingUser = uService.findById(id);

		if (bindingResult.hasErrors() || updatedFormUser.getUsername() == null) {
			model.addAttribute("users", uService.findAllSortedById());
			return "/users/edit";
		}

		if (uService.existsByUsername(updatedFormUser.getUsername())) {
			bindingResult.rejectValue("username", "error.username", "Username already in use");
			return "/users/edit";
		}

		updatedFormUser.addTicket(ticket);
		existingUser.setRoles(existingUser.getRoles());

		existingUser.setUsername(updatedFormUser.getUsername());
		existingUser.setName(updatedFormUser.getName());
		existingUser.setLastName(updatedFormUser.getLastName());

		uService.update(existingUser);

		attributes.addFlashAttribute("successMessage", "User with id " + updatedFormUser.getId() + ": "
				+ updatedFormUser.getUsername() + ", has been UPDATED!");

		// controllo per tornare al login nel caso sia l'utente a modificare le proprie
		// credenziali di accesso
		if (updatedFormUser.getUsername().equals(loggedUser.getUsername())) {
			return "redirect:/login";

		} else {
			return "redirect:/users/" + updatedFormUser.getId();
		}
	}

	// assign
	@GetMapping("/assign/{id}")
	public String assign(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("user", uService.findById(id));
		model.addAttribute("tickets", tService.findByStatus("To do"));

		return "users/assign";
	}

	
	//metodo assign, una sorta di piccola "edit" per assegnare un ticket partendo da uno user
	@PostMapping("/{userId}/assign-ticket")
	public String assignTicketToUser(@PathVariable Integer userId, @RequestParam("ticketId") Integer ticketId,
			Model model) {

		User user = uService.findById(userId);
		//ticketId arriva dal form di assign.html
		Ticket ticket = tService.findById(ticketId).get();

		//addTicket() è un metodo custom che ho creato per aggiungere un ticket alla lista di ticket dell'utente
		user.addTicket(ticket);
		ticket.setUser(user);
		
		//una volta assegnato il ticket all'utente modifico il suo stato, sicuramente va in 'Doing'
		ticket.setDoingStatus();

		uService.update(user);
		tService.update(ticket);

		return "redirect:/tickets/" + ticketId;
	}

	//metodo per aggiungere una nota ad un ticket partendo dal profilo di un utente
	@GetMapping("/{userId}/{ticketId}/addNote")
	public String addNote(@PathVariable Integer userId, @PathVariable("ticketId") Integer ticketId, Model model,
			Principal principal, Authentication authentication) {

		User loggedUser = uService.findByUsername(principal.getName());

		Ticket ticket = tService.findById(ticketId).get();
		Note addedNote = new Note();
		addedNote.setAuthor(loggedUser);
		addedNote.setTicket(tService.findById(ticketId).get());
		ticket.addNote(addedNote);

	
		model.addAttribute("ticketId", ticketId);
		model.addAttribute("userId", userId);
		model.addAttribute("note", addedNote);

		return "notes/create"; 
	}
	
	
	

	@PostMapping("/{userId}/setNotAtWork")
	public String updateUserAtWorkStatus(@PathVariable Integer userId, @RequestParam boolean notAtWork) {
		User user = uService.findById(userId);

		user.setNotAtWork(notAtWork);
		uService.update(user);

		return "redirect:/users/" + userId;
	}

}
