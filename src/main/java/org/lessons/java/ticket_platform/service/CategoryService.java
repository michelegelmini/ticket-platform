package org.lessons.java.ticket_platform.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java.ticket_platform.model.Category;
import org.lessons.java.ticket_platform.model.Ticket;
import org.lessons.java.ticket_platform.repo.CategoryRepository;
import org.lessons.java.ticket_platform.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findAllSortedById(){
		return repository.findAll(Sort.by("id"));
	}
	
	public List<Category> findByName(String title){
		return repository.findByNameContainingIgnoreCaseOrderByIdAsc(title);
	}
	
	public Category create(Category category) {
		return repository.save(category);
	}
	
	public Category update (Category category) {
		return repository.save(category);
	}
	
	public Category findById(Integer id){
		return repository.findById(id).get();
	}
	
	public void delete(Integer id) {
		repository.deleteById(id);
	}

}
