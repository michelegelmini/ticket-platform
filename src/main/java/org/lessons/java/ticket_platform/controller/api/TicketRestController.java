package org.lessons.java.ticket_platform.controller.api;

import java.util.List;

import java.util.Optional;

import org.lessons.java.ticket_platform.model.Ticket;
import org.lessons.java.ticket_platform.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestController {

	@Autowired
	private TicketService tService;
	


	@GetMapping
	public List<Ticket> index(@RequestParam(name = "word", required = false) String word,
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "status", required = false) String status, Model model) {
		List<Ticket> result;
		


	// filter by name

		if (word != null && !word.isEmpty()) {
			result = tService.findByTitle(word);
		} //filter by category
		else if (category != null) {
			model.addAttribute("category", category);
			result = tService.findByCategory(category);
		} //filter by status
		else if (status != null && !status.isEmpty()) {
			result = tService.findByStatus(status);
		} else {
			result = tService.findAllSortedById();
		}

		return result;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Ticket> show(@PathVariable Integer id) {

		Optional<Ticket> ticket = tService.findById(id);

		if (ticket.isPresent()) {
			return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(ticket.get(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public Ticket store(@Valid @RequestBody Ticket ticket) {
		Ticket newTicket = tService.create(ticket);
		return newTicket;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Ticket> update(@Valid @RequestBody Ticket ticket, @PathVariable Integer id) {

		Optional<Ticket> oldTicket = tService.findById(id);

		if (oldTicket.isPresent()) {
			Ticket foundTicket = tService.update(ticket);
			return new ResponseEntity<>(foundTicket, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Ticket> delete(@PathVariable Integer id) {
		Optional<Ticket> ticketToDelete = tService.findById(id);

		if (ticketToDelete.isPresent()) {
			tService.delete(id);
			return new ResponseEntity<Ticket>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
}
