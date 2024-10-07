package org.lessons.java.ticket_platform.repo;

import java.util.List;

import org.lessons.java.ticket_platform.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	public List<Category>findByNameContainingIgnoreCaseOrderByIdAsc(String title);

}
