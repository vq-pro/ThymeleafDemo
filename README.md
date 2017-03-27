# Thymeleaf Demo

A demo repository that contains an MVC controller, serving one page, 
and a REST server with CRUD fonctions. Everything is developped 
using test-first philosophy.

Full Cucumber-style integration tests for end-to-end, service-level, 
and a full suite of unit tests, resulting in near 100% coverage.

## Start Instructions

* Start Application
  * Run Application.java in the IDE
  * Or build and start the server from the command-line (Maven Required): 
    
    set spring.profiles.active=dev (or qa)  
    mvn clean package && java -jar target/ThymeleafDemo-1.0.0.war
    
* Go to [http://localhost:8080/thymeleafDemo/main](http://localhost:8080/thymeleafDemo/main)

## Testing

* You'll find plenty of unit tests in the src/test/java.
* All integration tests are run in Maven. In the IDE, launch CucumberIT and
  view the .feature files.
* There are two Spring profiles in use: dev and qa. DEV uses the H2 memory database,
  for responsiveness. 
  
  QA is a profile that uses a PostgreSQL database, but feel free to replace the 
  datasource definition in 
  _[application-qa.yml](src/main/resources/application-qa.yml)_. To enable a
  specific profile, use the property 'spring.profiles.active' (or define it as an
  environment variable).
  
  With Maven, or through the command-line, use the following:
  
  mvn clean verify -Dspring.profiles.active=qa

## Use Cases as Features

* The project is a sample Spring Boot project.
 
  Inspired by BDD, I've taken the approach of writing the use cases as features, 
  i.e. executable specifications. I chose to also write failure scenarios, where
  I wanted to make certain that the code would fail in a predictable manner. I
  took the side where when defining use cases, certain top-level error conditions
  also are written down in detail. So my features are descriptions, or use cases
  specifications.
  
  Here's how it works:
  
  * Starting with a rough scenario, making it pass. 
  * For each scenario step that is not working (or doesn't exist yet), 
    go down to the class itself, and locate the failing/missing test. 
  * Write the unit test, write the code, and pretty soon it's green.
  * We can go back to the scenario step, which now has all the code it needs.
  * Refactor the top-level Step files and scenarios, using Outline to generalize.

* Two levels: 
  * Frontend: _[UI](src/features/ui/UI.feature)_   
  * Backend: _[REST Server](src/features/service/RestServer.feature)_

* Thymeleaf is used for the presentation layer, with embedded Javascript but 
  no CSS or other decorations. 
  
  The focus was purely to complete basic functionality first. The template 
  demonstrates the different ways of embedding content into Thymeleaf, from 
  both Java and Javascript.

* REST Server with complete REST Client façade, with a mechanism 
  to transfer the user exceptions thrown by the server methods over to 
  be reinstanciated on the client side, automatically. When a user 
  exception, annotated with @ResponseStatus(HttpStatus.BAD_REQUEST), 
  is thrown by the server, what reaches the client side is a 
  HttpClientErrorException. The body contains a detailed response, in 
  JSON format, so it can be deserialized into an object which contains 
  the original exception name. The client then simply rethrows it on 
  its end!
  
* The REST Server is using a DAO, a 
  _[Database](src/main/java/quebec/virtualite/thymeleafdemo/backend/data/impl/DatabaseImpl.java)_ 
  interface encapsulating all database access. 
  
  Instead of directly using Spring Data Repository interfaces, 
  centralizing all access in a DatabaseImpl class provides the freedom 
  to split the persistent contents into different databases, without 
  needing to rewrite any REST Server code. The idea is to decouple.
  
  That also eases testing with a single database object to mock or to use.
  
* Both REST and MVC servers use @PathVariable, i.e. 
  /doSomething/24/New@20Item. 
  
  This simplifies tremendously all calls from Javascript as well!
  
* Last goody: a Spring bean that centralizes all Selenium page access,
  through a standard WebClient interface. Most useful test methods for 
  elements found on the page are included in a utility base class for all 
  UI Cucumber Steps files. 
  
  Normally, it is a problem to split the Step files into multiple ones, and 
  then sharing the step method definition. In other words, when a scenario 
  executes, the Step class instanciates itself with state, the one containing 
  the first step. 
  
  If the next step instruction is implemented using another Step file, this 
  second one wouldn't have the same state as the preceding instruction, which is 
  a problem. 
  
  So I've wrapped the WebClient inside a base utility class, 
  _[HtmlTestHelper](src/it/java/quebec/virtualite/utils/ui/HtmlTestHelper.java)_,
  which is a @Component (singleton), so all Step file will use the same @Autowired 
  WebClient. Step methods be shared freely inside of scenarios.
  

## License

[![CC0](https://licensebuttons.net/p/zero/1.0/88x31.png)](https://creativecommons.org/publicdomain/zero/1.0/)

To the extent possible under law, **Patrick Robert** has waived all copyright 
and related or neighboring rights to this work.