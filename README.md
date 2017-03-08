Andrio Paz da Fonseca Test
=========================


I have developed an RESTful WebService using Jersey for the JAX-RS implementation, Genson for the JSON to Java / Java to JSON data binding, EclipseLink for the JPA implementation and Derby for the In Memory Database.

Jersey is Oracles implementation of the JAX-RS 2.0 API.

Genson simply binds the data from JSON to Java and vice versa, you do not have to configure POJO mapping in the web.xml or specify @XmlRootElement to POJO objects.

EclipseLink implements the Java JPA 2.0 API which is used for transaction management, persistence configuration and data binding between tables and POJOs via annotations.

Derby is used to store data also allowing you to run an embedded database, in memory database etc.

Jersey Test Framework 2.5.1 will execute unit tests and allow for the http response to be tested, this has been combined with Mockito to mock out the services however if you wish based out your configuration you can also perform end to end tests.

----------------------------
Database
-------------------------
Jersey 2.5.1 RESTful Web Service with JPA 2.1 implemented using EclipseLink 2.5.2 and using Derby 10.10.1.1 In Memory Database
I did a SQL script called create-script, it will create tables and insert some initial values to CRUD.

-------------------------
Test
-------------------------

The project has a UserResourceTest implemented using JUNIT.


-------------------------
JSF 2.0 WAR File
-------------------------

Once you have started up the application

http://localhost:8080/jrws
- Will navigate you to the index.xhtml to ensure the application is running.


----------------------
Restful Calls
----------------------

Ensure you set the content-type in your request to application/json

Department Restful API

http://localhost:8080/jrws/rest/department/list
http://localhost:8080/jrws/rest/department/update/{userId}/{name}/{description}
http://localhost:8080/jrws/rest/department/save/{name}/{description}
http://localhost:8080/jrws/rest/department/retrieve/{id}
http://localhost:8080/jrws/rest/department/delete/{id}
Users Restful API

http://localhost:8080/jrws/rest/user/list
http://localhost:8080/jrws/rest/user/update/{userId}/{name}/{description}
http://localhost:8080/jrws/rest/user/save/{name}/{description}
http://localhost:8080/jrws/rest/user/retrieve/{id}
http://localhost:8080/jrws/rest/user/delete/{id}
Permission Restful API

http://localhost:8080/jrws/rest/permission/list
http://localhost:8080/jrws/rest/permission/update/{userId}/{name}/{description}
http://localhost:8080/jrws/rest/permission/save/{name}/{description}
http://localhost:8080/jrws/rest/permission/retrieve/{id}
http://localhost:8080/jrws/rest/permission/delete/{id}
