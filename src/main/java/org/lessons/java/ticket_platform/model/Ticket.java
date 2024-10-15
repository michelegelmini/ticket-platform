package org.lessons.java.ticket_platform.model;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tickets")
public class Ticket {

//variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "TITLE is required")
	private String title;

	
	@NotBlank(message = "CONTENT is required")
	private String content;

	@NotNull
	@Min(1)
	@Max(5)
	private int priority;

	private String status;

	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonBackReference
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	@OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE)
	@JsonManagedReference
	private List<Note> notes;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;

	@UpdateTimestamp
	private Timestamp updatedAt;

	@UpdateTimestamp
	private LocalTime deletedAt;

	@Transient
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm | dd-MM-yyyy");

//getters and setters	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public void addNote(Note note) {
		this.notes.add(note);
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

//	public void setCreatedAt(Timestamp createdAt) {
//		this.createdAt = createdAt;
//	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalTime deletedAt) {
		this.deletedAt = deletedAt;
	}

	public int getPriority() {

		if (priority == 0) {
			return 1;
		} else {
			return priority;
		}
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DateTimeFormatter getDateFormatter() {
		return dateFormatter;
	}

	public void setDateFormatter(DateTimeFormatter dateFormatter) {
		this.dateFormatter = dateFormatter;
	}

	// custom methods
	public void setDoingStatus() {
		if (user == null || user.getId() == null) {
			this.setStatus("To do");
		} else {
			this.setStatus("Doing");
		}
		System.out.println("Status set to: " + this.getStatus());
	}

	public String getFormattedCreatedAt() {
		return createdAt.toLocalDateTime().format(dateFormatter);
	}

	public String getFormattedUpdatedAt() {
		if (this.updatedAt != null) {
			return updatedAt.toLocalDateTime().format(dateFormatter);
		} else {
			return null;
		}
	}

}
