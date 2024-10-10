package org.lessons.java.ticket_platform.service;

import java.util.List;
import java.util.Optional;

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

	public Optional<Ticket> findById(Integer id) {
		return repository.findById(id);
	}
	
	public Ticket findSelectedById(Integer id) {
		return repository.findById(id).get();
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}
	
	public List<Ticket> findByCategory(String category) {
		return repository.findByCategoryNameContainingIgnoreCaseOrderByIdAsc(category);
	}
	
	public List<Ticket> findByStatus(String status){
		return repository.findByStatusContainingIgnoreCaseOrderByIdAsc(status);
	}
	
	public List<Ticket> findByUser(int userId){
		return repository.findByUserId(userId);
	}
	
	public List<Ticket> findByUserAndTitle(int userId, String title){
		return repository.findByUserIdAndTitleContainingIgnoreCaseOrderByIdAsc(userId, title);
	}
	
	public List<Ticket> findByUserAndCategory(int userId, String category){
		return repository.findByUserIdAndCategoryNameContainingIgnoreCaseOrderByIdAsc(userId, category);
	}
	
	public List<Ticket> findByUserAndStatus(int userId, String status){
		return repository.findByUserIdAndStatusContainingIgnoreCaseOrderByIdAsc(userId, status);
	}

}
