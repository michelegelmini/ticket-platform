package org.lessons.java.ticket_platform.service;

import java.util.List;

import org.lessons.java.ticket_platform.model.Note;

import org.lessons.java.ticket_platform.repo.NoteRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class NoteService {

	@Autowired
	private NoteRepository repository;
	
	public List<Note> findByTicketId(int id){
		return repository.findByTicketId(id);
	}

	public Note create(Note note) {
		return repository.save(note);
	}

	public Note update(Note note) {
		return repository.save(note);
	}

}
