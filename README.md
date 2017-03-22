# just-gif-it
Spring boot example project

## Command Line Project Creation
`curl start.spring.io/starter.zip -o demo.zip --data @options.curl`
see the options file for dependencies and project name, artifact id etc.

## Upload Video With CURL
`curl -F file=@cats.mp4 -F start=0 -F end=0 -F speed=1 -F repeat=0 localhost:8080/upload`

## Properties
* `com.fererlab.create-result-dir #true/false`
* `com.fererlab.optimize #true/false`

## Spring Boot Configuration Resolution

#### 1. Command Line Args
* prefix property with double dash

`--server.port=9000`
`--server.config.name=config`
`--debug`

#### 2. Inline JSON, Environment Variable
* Embedded JSON in SPRING_APPLICATION_JSON env var 

`SPRING_APPLICATION_JSON='{"server":{"port":"9000"}}'`

#### 3. Standard Servlet Environment

* Has a hierarchy within itself 
    
    a) `ServletConfig` init parameters  
    b) `ServletContext` init parameters  
    c) JNDI attributes  
    d) `System.getProperties()`  
    d) OS environment variables  

#### 4. Random Value Property Class
* RandomValuePropertySource
    * ${random.*} replacements
    * can be one of
        * value
        * int
        * long
        * int(< number>)  between zero and number
        * int[< number1>, < number2>] between numbers
 
#### 5. Application Properties
* application.properties/YAML 
    1. Look for profile specific configuration 1st
        * application-{profile}.properties 
        * application-{profile}.yml
         
    2. Look for the generic configuration 2nd
        * application.properties / application.yml 
        
    3. Check these locations
        * $CWD/config AND $CWD  (CWD current working dir)
        * classpath:/config AND classpath:
        
#### 6. @PropertySource annotation
* Can be defined for property files not YAML files 

```java
@SpringBootApplication
@PropertySource("/path/to/other.properties")
public class Application{}
```
       
#### 7. Default Properties
* Can be defined at the spring application class 

```java
@SpringBootApplication
public class Application{
    public static void main(String[] args){
        //Map<String, Object> defaultProperties 
        //OR 
        //Properties defaultProperties 
        SpringApplication.setDefaultProperties(defaultProperties);
    }
}
```
        
## Actuators - Builtin Production Ready Endpoints

* `/autoconfig` for reporting
* `/beans` for all beans
* `/configprops` for all config
* `/dump` for memory dump
* `/health` to check application
* [rest of the endpoints](http://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)

#### Request to /health

Spring boot creates a password for default user 'user', you may find the password in the logs 'Using default security password: HERE_THE_PASSWORD'

A GET request to /health only returns a response as

```json
{
  "status":"UP"
} 
```

same request with the user and password as basic auth, should return more information

```json
{
  "status": "UP",
  "justGifIt": {
    "status": "UP",
    "JustGifIt-Key": "JustGifIt-Key"
  },
  "diskSpace": {
    "status": "UP",
    "total": 120101797888,
    "free": 21861167104,
    "threshold": 10485760
  }
}
```
 

 





