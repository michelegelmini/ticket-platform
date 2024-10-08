package org.lessons.java.ticket_platform.service;

import java.util.List;
import java.util.Optional;


import org.lessons.java.ticket_platform.model.User;

import org.lessons.java.ticket_platform.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	
	public List<User> findAllSortedById(){
		return userRepository.findAll(Sort.by("id"));
	}

	public List<User> findByUsername(String username) {
		return (List<User>) userRepository.findByUsernameContainingIgnoreCaseOrderByIdAsc(username).get();
	}

	public User create(User user) {
		return userRepository.save(user);
	}

	public User update(User user) {
		return userRepository.save(user);
	}  

	public User findById(Integer id) {
		return userRepository.findById(id).get();
	}

	public void delete(Integer id) {
		userRepository.deleteById(id);
	}
	
}


