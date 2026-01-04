package com.suryacart.user.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.suryacart.user.model.dto.ContactDTO;
import com.suryacart.user.model.entity.Contacts;

@Component
public class ContactMapper {

	private ModelMapper modelMapper;

	public void mapContactDTOToContact(ContactDTO contactDTO, Contacts contacts) {
		// Implement mapping logic here
		modelMapper = new ModelMapper();
		modelMapper.map(contactDTO, contacts);
	}

	public void mapContactToContactDTO(Contacts contact, ContactDTO contactDTO) {
		// Implement mapping logic here
		modelMapper = new ModelMapper();
		modelMapper.map(contact, contactDTO);
	}
}
