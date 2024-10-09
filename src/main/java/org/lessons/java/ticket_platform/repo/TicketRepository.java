package org.lessons.java.ticket_platform.repo;

import java.util.List;

import org.lessons.java.ticket_platform.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
	
	public List<Ticket>findByTitleContainingIgnoreCaseOrderByIdAsc(String title);
	
	public List<Ticket>findByCategoryNameContainingIgnoreCaseOrderByIdAsc(String category);
	
	public List<Ticket>findByStatusContainingIgnoreCaseOrderByIdAsc(String status);
	
	public List<Ticket>findByUserId(int userId);
	
	public List<Ticket>findByUserIdAndTitleContainingIgnoreCaseOrderByIdAsc(int userId, String title);
	
	public List<Ticket>findByUserIdAndCategoryNameContainingIgnoreCaseOrderByIdAsc(int userId, String category);
	
	public List<Ticket>findByUserIdAndStatusContainingIgnoreCaseOrderByIdAsc(int userId, String status);

}
