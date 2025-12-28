package com.suryacart.user.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.suryacart.user.Constant.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue
	private UUID id;

	@NotBlank
	@Size(min = 2, max = 20)
	private String name;

	@Column(unique = true, nullable = false)
	@Email
	private String email;

	@NotBlank
	@Size(min = 8, max = 60)
	private String password;

	private String image;

	@Enumerated(EnumType.STRING)
	private Role role;

	private boolean enabled;

	@Column(length = 1000)
	private String information;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Contacts> contacts;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@PrePersist
	void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
