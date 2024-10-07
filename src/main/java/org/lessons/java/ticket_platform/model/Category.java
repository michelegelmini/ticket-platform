package org.lessons.java.ticket_platform.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category {
	//variables
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@NotNull
		@Column(unique=true)
		private String name;
		
		@NotNull
		@Size(min=1, max=5)
		private int priority;
		
		@ManyToMany(mappedBy = "categories")
		private List<Ticket> tickets;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getPriority() {
			return priority;
		}

		public void setPriority(int priority) {
			this.priority = priority;
		}

		public List<Ticket> getTickets() {
			return tickets;
		}

		public void setTickets(List<Ticket> tickets) {
			this.tickets = tickets;
		}
}
