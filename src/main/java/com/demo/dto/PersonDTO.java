package com.demo.dto;

public class PersonDTO {
	
	private Long id;
    private String name;
    private String email;
    private Long number;
    
    public PersonDTO () {
    }
    
	public PersonDTO(Long id, String name, String email, Long number) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.number = number;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
    
}
