package org.lessons.java.ticket_platform.repo;

import java.util.List;

import org.lessons.java.ticket_platform.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {

	public List<Note> findById(int id);
}
