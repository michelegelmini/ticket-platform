package org.lessons.java.ticket_platform.repo;


import java.util.Optional;

import org.lessons.java.ticket_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public Optional<User> findByUsername(String username);


	public Optional<User> findByUsernameContainingIgnoreCaseOrderByIdAsc(String username);
	
	
}
