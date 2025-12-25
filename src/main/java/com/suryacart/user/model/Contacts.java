package com.suryacart.user.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "CONTACTS")
public class Contacts {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	private String cname;
	private String cnickname;
	private String cwork;

	@Column(unique = true)
	private String cemail;

	@Transient
	private MultipartFile cimage;

	@Column(length = 1000)
	private String cdiscription;
	@Column(length = 10)
	private String cphoneNumber;

	@ManyToOne
	private User user;

}
