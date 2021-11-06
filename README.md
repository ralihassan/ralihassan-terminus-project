# terminus-project

This is a sample spring boot application to save and retrieve person's details like name, email and phone number using Hibernate, Spring Data and MySQL. Please visit localhost:8080 after starting the project to view the running app.

# REST End Points Details

/person/get/{id}: Gets the person details based on id parameter passed with the help of cached query.

/person/clean:    It cleans the records in the cache.

/person/save:     Saves the preson details. It also validates if mandatory attributes are present.

/person/all:      It returns details of all records saved in database
