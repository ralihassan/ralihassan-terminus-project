package com.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.domain.Person;
import com.demo.dto.PersonDTO;
import com.demo.service.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping("/get/{id}")
	@ResponseBody
	public PersonDTO getPersonById(@PathVariable(name = "id") Long personId) {
		return personService.getPersonById(personId);
	}
	
	@PostMapping("/save")
	@ResponseBody
	public PersonDTO savePerson(@RequestBody PersonDTO personDTO, 
			HttpServletResponse response) throws IOException {
		
		// validate if name and email fields are not missing
		boolean validation = validateRequest(personDTO.getName(), personDTO.getEmail());
		
		if(validation == false) {
			response.sendRedirect("error");
		}
		
		return personService.savePerson(personDTO);
	}

	@GetMapping("/all")
	@ResponseBody
	public List<PersonDTO> getPersonList() {
		return personService.getAllPersons();
	}
	
	@GetMapping("/clean")
	@ResponseBody
	public String cleanCache() {
		String returnMsg = "cache enteries has been removed";
		personService.cleanCache();
		return returnMsg;
	}
	
	@GetMapping("/error")
	@ResponseBody
	public String getErrorMessage() {
		String errorMsg = "Something went wrong while processing the request";
		return errorMsg;
	}
	
	private boolean validateRequest(String name, String email) {
		boolean result = true;
		
		if(name == null || name.trim() == "" || 
				email == null || email.trim() == "") {
			result = false;
		}
		
		return result;
	}

}