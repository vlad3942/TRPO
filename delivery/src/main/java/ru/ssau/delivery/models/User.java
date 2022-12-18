package ru.ssau.delivery.models;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@Table(name = "users",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "username"),
				@UniqueConstraint(columnNames = "email"),
				@UniqueConstraint(columnNames = "phone")
		})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	private String email;
	private String phone;
	private String password;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(String username, String email, String phone, String password) {
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.password = password;
	}
}
