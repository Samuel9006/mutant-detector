# mutant-detector
Detects if a human is a mutant based on an NxN DNA array

---
You can see the application running in the following url:

- http://ec2-34-204-7-206.compute-1.amazonaws.com:8080/swagger-ui/index.html

---

### Prerequisites

* Java 11
* git
* Gradle

---

###set in your local

clone the repository:

```sh
$ git clone https://github.com/Samuel9006/mutant-detector.git
$ cd mutant_detector
```

Before building the project make sure to configure the connection with your database.
- open the properties file ***mutant-detector/src/main/resources/application.yml*** whith a code editor
- replace the word `yourUrl` by your jdbc connection url. Example ***jdbc:postgresql://localhost:5432/mutant*** 
- replace the word `yourUser` and `yourPass` by your user and password database respectly. 

####build project

```sh
$ .\gradlew clean build
```
####Generate the coverage report:
```sh
$ .\gradlew jacocoTestReport
```
generated report: ***mutant-detector\build\reports\jacoco\test\html\index.html***

####Run
```sh
$ .\gradlew bootRun
```
The app will be running in your local by the port `8080`

As soon as the app is running, you can validate its operation by entering from your browser :
http://localhost:8080/swagger-ui/index.html


## Test Services
From swagger-ui you can test services
- http://localhost:8080/swagger-ui/index.html `LOCAL`
- http://ec2-34-204-7-206.compute-1.amazonaws.com:8080/swagger-ui/index.html `PROD`

### /mutant/
* Método: POST
* EndPoint: /mutant/
* Request:
    * is mutant
    ```
  {
  "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
  }
    ```
    * is human
    ```
  {
  "dna": ["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]
  }
    ```


### /stats/
* Método: GET
* EndPoint: /stats/
* Response:
    ```
  {
    "ratio": 1.5,
    "count_mutant_dna": 3,
    "count_human_dna": 2
  }
    ```
