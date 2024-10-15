package org.lessons.java.ticket_platform.model;

import java.util.ArrayList;
import java.util.List;

import java.util.Set;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull
	@NotEmpty
	private String username;

	@NotNull
	@NotEmpty
	private String password;

	private String firstName;

	private String lastName;

	private String picture;

	private boolean isAvailable;
	
	private boolean notAtWork;

	public boolean isNotAtWork() {
		return notAtWork;
	}

	public void setNotAtWork(boolean notAtWork) {
		this.notAtWork = notAtWork;
	}

	@ElementCollection
	private List<Ticket> ticketsInProgress;
	
	@ElementCollection
	private List<Ticket> finishedTickets;

	@ManyToMany(fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<Role> roles;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Ticket> tickets;
	
//	@Formula("(SELECT tickets.id "
//			+ "from tickets "
//			+ "INNER JOIN users on tickets.id = users.id "
//			+ "WHERE tickets.status = 'Doing')")

	@OneToMany(mappedBy = "author")
	private List<Note> notes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getName() {
		return firstName;
	}

	public void setName(String name) {
		this.firstName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFormattedName() {
		return this.firstName + " " + this.lastName;
	}
	

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public void setAvailability() {
		for (Ticket ticket : tickets) {
			String statusToCheck = ticket.getStatus();
			if (statusToCheck.equals("Doing")) {
				this.setAvailable(false);		
			} 
		}
		this.setAvailable(true);	
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}
	
	public boolean isAvailable() {
		for (Ticket ticket : tickets) {
			String statusToCheck = ticket.getStatus();
			if (statusToCheck.equals("Doing")) {
				return false;		
			} 
		}
		return true;			
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public void addTicket(Ticket ticket) {
		tickets.add(ticket);
		ticket.setUser(this);
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Ticket> getTicketsInProgress() {
	    if (ticketsInProgress == null) {
	        ticketsInProgress = new ArrayList<>();
	    }
	    ticketsInProgress.clear();
	    for (Ticket ticket : tickets) {
	        if(ticket.getStatus().equals("Doing")) {
	            ticketsInProgress.add(ticket);
	        }
	    }
	    return ticketsInProgress;
	}
	
	public List<Ticket> getfinishedTickets(){
		if (finishedTickets == null) {
			finishedTickets = new ArrayList<>();
	    }
		finishedTickets.clear();
	    for (Ticket ticket : tickets) {
	        if(ticket.getStatus().equals("Done")) {
	        	finishedTickets.add(ticket);
	        }
	    }
	    return finishedTickets;
	}
	    

//	public void setTicketsInProgress(List<Ticket> ticketsInProgress) {
//		this.ticketsInProgress = ticketsInProgress;
//	}

}