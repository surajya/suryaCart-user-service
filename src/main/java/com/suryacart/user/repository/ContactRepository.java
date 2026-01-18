package com.suryacart.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suryacart.user.model.entity.Contacts;

@Repository
public interface ContactRepository extends JpaRepository<Contacts, UUID> {

}
