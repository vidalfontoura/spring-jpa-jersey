# Skills in Spring, Data manipulation and JAX RS
Here you will find a scaffold of a project that aims to expose a REST service to list cities.
You need to upgrade the project to the newest versions and implement this service using any necessary means.

- Java (preferably Java 8 and Functional programming as much as possible)
- RESTFull service
- Data manipulation layer
- Spring 4 and Spring-boot
- Maven

# Database
The actual implementation uses H2 in memory as the database. You will find also the scripts 
for MySQL. The scripts insert a few entries in each table.

# Proposed exercise
The candidate must:
- Create Entity classes for the tables, including relationships
- Create the Data manipulation layer. Feel free to use structure or framework you like (JPA, JDBC, Spring Data, etc).
- Create a GET REST service to retrieve the list of cities in the database, and return them as a JSON object.
- The service may receive the query param "country" as a String, to restrict the search. The parameter may be part of the Country name
   http://server:port/rest/cities[?country=name]

- Create an operation to load data into the database (Here you're free to be creative. You can load data from a simple CSV, a spreadsheet, a rest service, etc...)

Feel free to modify the files included, upgrade frameworks, add or remove packages, in every aspect you want. Just check the note regarding JUnit tests below.

# Expected results
After the implementation, the application should run after the following command line:

	java -jar target/spring-jpa-jersey.jar
    
or 

    mvn spring-boot:run
    
or deploy on Tomcat, or Jetty or an Application Server, as long as you include instructions for the deploy.


Then, open a browser and type :

    http://localhost:8090/rest/cities?country=Uni


It must return, at least the following (ids may vary) :

    [
        {
            "id":86,
            "name":"New York",
            "country":{
                "id":2,
                "name":"United States"
            }
        },
        {
            "id":87,
            "name":"Los Angeles",
            "country":{
                "id":2,
                "name":"United States"
            }
        },
        {
            "id":88,
            "name":"Atlanta",
            "country":{
                "id":2,
                "name":"United States"
            }
        }
    ]


# Unit tests

Included you will find JUnit tests, with commented lines. Those tests must run after the lines
are uncommented.

** PLUS: It would be great if you can come up with unit and integration tests separately in their apropriate building phases.

# Implementation Details

Two Rest end points were created GET and PUT. 
The GET end point will return either all cities existing in the database or the cities from the given query parameter "country". If there is not any cities for the provided country it will return the Http Status No Content. The operation can be tested locally using the following curls:

	curl http://localhost:8090/rest/cities
	
	curl http://localhost:8090/rest/cities?country=Br

The PUT end point receive a json file containing the cities and simply inserts into the database. The operation can be tested locally using the following curl:

	curl -H "Content-Type: application/json" -X PUT -d '[{"name":"Ponta Grossa","country":{"name":"Brazil"}},
	{"name":"Cascavel","country":{"name":"Brazil"}}, {"name":"Oslo","country":{"name":"Norway"}}]' http://localhost:8090/	rest/cities


Batch Processing with Scheduling was added to load an .csv file in the database once the file is processed the operation deletes it, its necessary to configure two properties in the yml configure files:

	batch:
    	file:
    	cron-expression: 

The batch.file defines the name of the file to be load in the database. 

The batch.cron-expression defines the cron expression for the scheduling rate. 
    
Note: Currently supporting only files within the ClassPath. I think for this feature become more useful it should 		read files from external sources such S3 or even a external folder in the running server. Although I tried to explore and learn the batch and scheduling from the spring-boot for this exercise.

#Notes and Improvements







