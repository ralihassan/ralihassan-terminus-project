package com.demo.service;

import java.util.List;
import com.demo.dto.PersonDTO;

public interface PersonService {

	public List<PersonDTO> getAllPersons();
	
	public PersonDTO getPersonById(Long personId);

	public PersonDTO savePerson(PersonDTO personDTO);
	
	public void cleanCache();
	
	
}
