package org.lessons.java.ticket_platform.repo;

import java.util.List;

import org.lessons.java.ticket_platform.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer>{
	
	public List<Status>findByNameContainingIgnoreCaseOrderByIdAsc(String name);

}
