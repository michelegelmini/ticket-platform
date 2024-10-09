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

//	@Autowired
//	private DatabaseUserDetailsService dbService;

	@GetMapping
	public String index(Model model, @RequestParam(name = "username", required = false) String username,
			Principal principal, Authentication authentication) {

		List<User> userList;
		User loggedUser = uService.findByUsername(principal.getName()); 

		if (loggedUser.getUsername().equals("admin")) {
			model.addAttribute("username", username);
			model.addAttribute("username", authentication.getName());
		} else {
			username = loggedUser.getUsername();
		}

		if (username != null && !username.isEmpty()) {
			model.addAttribute("userUsername", username);
			userList = uService.searchByUsername(username);

		} else {
			// prendo i dati da consegnare a users
			userList = uService.findAllSortedById();
		}

		// li inserisco nel modello
		model.addAttribute("tickets", tService.findAllSortedById());
		model.addAttribute("users", userList);
		
		username = loggedUser.getUsername();
		if (username.equals("admin")) {
			return "/users/index";
		} else {
			return "redirect:/users/" + loggedUser.getId();
		}
	}

	// show
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer userId, Model model, Principal principal, Authentication authentication) {
		model.addAttribute("user", uService.findById(userId));
		model.addAttribute("tickets", tService.findByUser(userId));
		

		return "/users/show";
	}

	// create
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("user", new Ticket());
		model.addAttribute("categories", uService.findAllSortedById());
		return "/users/create";
	}

	// Store
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("user") User formUser, BindingResult bindingResult,
			RedirectAttributes attributes, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", uService.findAllSortedById());
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

		// restituisco la view con il model inserito
		return "users/edit";
	}

	// update
	@PostMapping("/edit/{id}")
	public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("user") User updatedFormUser, Ticket ticket,
			RedirectAttributes attributes, Model model, Authentication authentication, Principal principal) {

		List<Ticket> ticketList;
		User loggedUser = uService.findByUsername(principal.getName());
		int loggedUserId = loggedUser.getId();
		User existingUser = uService.findById(id);
		
		// salva lo user
		// Aggiungi il ticket alla lista dei ticket dell'utente
		updatedFormUser.addTicket(ticket);
		existingUser.setRoles(existingUser.getRoles());
		
		existingUser.setUsername(updatedFormUser.getUsername());
	    existingUser.setPassword(updatedFormUser.getPassword());
	    existingUser.setName(updatedFormUser.getName());
	    existingUser.setLastName(updatedFormUser.getLastName());

		// Aggiorna l'utente con il nuovo ticket nella lista
		uService.update(existingUser);

		attributes.addFlashAttribute("successMessage", "User with id " + updatedFormUser.getId() + ": "
				+ updatedFormUser.getUsername() + ", has been UPDATED!");

		if (loggedUser.getUsername().equals("admin")) {
			return "redirect:/users/"+updatedFormUser.getId();
		} else {
			return "redirect:/login";
		}
	}

	// delete
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {

		// deleteById cerca ed elimina in un unico comando

		User userToDelete = uService.findById(id);

		uService.delete(id);

		attributes.addFlashAttribute("deletedMessage",
				"User with id " + id + ": " + userToDelete.getUsername() + ", has been DELETED!");

		return "redirect:/users";
	}

	// assign
	@GetMapping("/assign/{id}")
	public String assign(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("user", uService.findById(id));
		model.addAttribute("tickets", tService.findByStatus("To do"));

		// restituisco la view con il model inserito
		return "users/edit";
	}

	@PostMapping("/{userId}/assign-ticket")
	public String assignTicketToUser(@PathVariable Integer userId, @RequestParam("ticketId") Integer ticketId,
			Model model) {
		// Trova l'utente esistente
		User user = uService.findById(userId);
//		if (user == null) {
//			model.addAttribute("error", "User not found.");
//			return "error";
//		}
//
//		// Trova il ticket esistente
		Ticket ticket = tService.findById(ticketId);
//		if (ticket == null) {
//			model.addAttribute("error", "Ticket not found.");
//			return "error";
//		}

		// Aggiorna l'associazione
		user.addTicket(ticket); // Aggiunge il ticket alla lista dell'utente
		ticket.setUser(user); // Imposta l'utente nel ticket
		ticket.setDoingStatus();

		// Salva l'utente aggiornato
		uService.update(user);
		tService.update(ticket);

		// Ritorna alla pagina dell'utente
		return "redirect:/users"; // Reindirizza alla pagina di modifica dell'utente
	}

	@PostMapping("/{userId}/addNote")
	public String addNote(@PathVariable Integer userId, @RequestParam("ticketId") Integer ticketId,
			@RequestParam("note") Note note, Model model) {
		User user = uService.findById(userId);
		Ticket ticket = tService.findById(ticketId);
		ticket.addNote(note);

		// Salva l'utente aggiornato
		uService.update(user);
		tService.update(ticket);

		// Ritorna alla pagina dell'utente
		return "redirect:/tickets"; // Reindirizza alla pagina di modifica dell'utente
	}

}
