package org.lessons.java.ticket_platform.service;

import java.util.List;

import org.lessons.java.ticket_platform.model.Ticket;
import org.lessons.java.ticket_platform.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

	@Autowired
	private TicketRepository repository;

	public List<Ticket> findAllSortedById() {
		return repository.findAll(Sort.by("id"));
	}

	public List<Ticket> findByTitle(String title) {
		return repository.findByTitleContainingIgnoreCaseOrderByIdAsc(title);
	}

	public Ticket create(Ticket ticket) {
		return repository.save(ticket);
	}

	public Ticket update(Ticket ticket) {
		return repository.save(ticket);
	}

	public Ticket findById(Integer id) {
		return repository.findById(id).get();
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

}
