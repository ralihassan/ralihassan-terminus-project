package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.demo.domain.Person;
import com.demo.dto.PersonDTO;
import com.demo.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService{
	
	@Autowired
	private PersonRepository personRepository;
	
	public List<PersonDTO> getAllPersons(){
		
		List<Person> personList = personRepository.findAll();
		List<PersonDTO> personDTOList = getPeronDTOList(personList);
		return personDTOList;
	}
	
	@Cacheable(value="person", key="#personId")
	public PersonDTO getPersonById(Long personId) {
		
		PersonDTO personDTO = new PersonDTO();
		Optional<Person> person = personRepository.findById(personId);
		
		if(person.isPresent()) {
			personDTO = getPeronDTO(person.get());
			return personDTO;
		} 
		
		return personDTO;
	}
	
	@Transactional
	public PersonDTO savePerson(PersonDTO dto) {
		
		Person person = new Person(dto.getName(), dto.getEmail(), dto.getNumber());
		person = personRepository.save(person);
		
		return getPeronDTO(person);
	}
	
	@CacheEvict(value="person", allEntries=true)
	public void cleanCache() {
		//log message that cache has been cleaned
	}

	private List<PersonDTO> getPeronDTOList(List<Person> personList) {
		List<PersonDTO> personDTOList = new ArrayList<PersonDTO>();
		
		for(int i=0; i<personList.size(); i++) {
			
			PersonDTO dto = new PersonDTO(personList.get(i).getId(), 
					personList.get(i).getName(), personList.get(i).getEmail(), 
					personList.get(i).getNumber());
			
			personDTOList.add(dto);
		}
		
		return personDTOList;
	}

	private PersonDTO getPeronDTO(Person person) {
		
		PersonDTO dto = new PersonDTO(person.getId(), person.getName(),
				person.getEmail(), person.getNumber());
		
		return dto;
	}
}
