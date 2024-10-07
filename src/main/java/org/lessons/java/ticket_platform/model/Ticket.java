package org.lessons.java.ticket_platform.model;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tickets")
public class Ticket {

//variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String title;

	@NotNull
	private String content;

	private String notes;
	
	@ManyToOne
	@JoinColumn(name="status_id")	
	private Status status;
	
	@ManyToMany()
	@JoinTable(
			name = "tickets_categories",
			joinColumns = @JoinColumn(name = "ticket_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")
			)
	private List<Category> categories;
	
	@OneToOne
	@JoinColumn(name="operator_id")
	private Operator operator;

	@CreationTimestamp
	private Timestamp createdAt;

	@UpdateTimestamp
	private Timestamp updatedAt;

	@UpdateTimestamp
	private LocalTime deletedAt;
	
	@Transient
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

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

}
