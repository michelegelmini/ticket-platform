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

//	@GetMapping
//	public String index(Model model, @RequestParam(name = "name", required = false) String name) {
//
//		model.addAttribute("noteName", name);
//		// model.addAttribute("username", authentication.getName());
//
//		List<Note> noteList;
//
//		if (name != null && !name.isEmpty()) {
//			model.addAttribute("ticketTitle", name);
//			noteList = nService.findByName(name);
//
//		} else {
//			// prendo i dati da consegnare a tickets
//			noteList = nService.findAllSortedById();
//		}
//
//		// li inserisco nel modello
//		model.addAttribute("categories", noteList);
//
//		return "/categories/index";
//	}

	// show
//	@GetMapping("/{id}")
//	public String show(@PathVariable("id") Integer ticketId, Model model) {
//		model.addAttribute("ticket", tService.findById(ticketId));
//		return "/tickets/show";
//	}
//	
//	// create
//	@GetMapping("/create")
//	public String create(Model model) {
//		model.addAttribute("note", new Note());
//		return "/notes/create";
//	}

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

// edit
//	@GetMapping("/edit/{id}")
//	public String edit(@PathVariable("id") Integer id, Model model) {
//
//		model.addAttribute("note", nService.findById(id));
//
//		// restituisco la view con il model inserito
//		return "categories/edit";
//	}
//
//	// update
//	@PostMapping("/edit/{id}")
//	public String update(@Valid @ModelAttribute("note") Note updatedFormNote, BindingResult bindingResult,
//			RedirectAttributes attributes, Model model) {
//
//		// se ci sono errori nel form, mostra gli errori
//		if (bindingResult.hasErrors()) {
//			model.addAttribute("categories", nService.findAllSortedById());
//			return "/tickets/edit";
//		}
//		// altrimenti salva il ticket
//		nService.update(updatedFormNote);
//
//		attributes.addFlashAttribute("successMessage",
//				"Ticket with id " + updatedFormNote.getId() + ": " + updatedFormNote.getName() + ", has been UPDATED!");
//
//		return "redirect:/tickets";
//	}
//
//	// delete
//	@PostMapping("/delete/{id}")
//	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
//
//		// deleteById cerca ed elimina in un unico comando
//
//		Note noteToDelete = nService.findById(id);
//		nService.delete(id);
//
//		attributes.addFlashAttribute("deletedMessage",
//				"Ticket with id " + id + ": " + noteToDelete.getName() + ", has been DELETED!");
//
//		return "redirect:/categories";
//	}
//
//}
