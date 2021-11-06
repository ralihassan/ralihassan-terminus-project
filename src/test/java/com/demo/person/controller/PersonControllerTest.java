package com.demo.person.controller;

import org.junit.After;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.PersonApplication;
import com.demo.domain.Person;
import com.demo.repository.PersonRepository;
import com.demo.person.util.JsonUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PersonApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonControllerTest {
	
	@Autowired
    private MockMvc mvc;

    @Autowired
    private PersonRepository repository;
    
    @After
    public void resetDb() {
        repository.deleteAll();
    }
    
    @Test
    public void testSavePersonEntityEndpoint() throws IOException, Exception {
    	Person hassan = new Person("hassan", "hassan@test.com", 345l);
        mvc.perform(post("/person/save").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(hassan)));

        List<Person> found = repository.findAll();
        assertThat(found).extracting(Person::getName).containsOnly("hassan");
    }
    
    @Test
    public void testPersonListEndpoint() throws Exception {
        createTestPerson("ali", "ali@test.com", 123l);

        mvc.perform(get("/person/all").contentType(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
          .andExpect(jsonPath("$[0].name", is("ali")));
    }


    private void createTestPerson(String name, String email, Long number) {
        Person person = new Person(name, email, number);
        repository.saveAndFlush(person);
    }

}
