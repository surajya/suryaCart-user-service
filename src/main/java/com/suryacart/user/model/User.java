package com.suryacart.user.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "USERS")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	@NotBlank(message = "Name must be required")
	@Size(min = 2,max = 20, message = "Size must be between 2 - 20 character")
	private String name;
	
	@Column(unique = true)
	@NotBlank(message="Email must be required")
	@Email
	private String email;
	
	@NotBlank(message = "Password must be required")
	@Size(min = 3,max = 60, message = "password.register.size")
	private String password;
	private String image;
	private String role;
	private String isEnable;
	@Column(length = 1000)
	private String information;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Contacts> contacts;
}
