# CM Euro FX Application

## Intro
This application loads the available currencies and historical foreign exchange rates to the in memory H2 database during initial startup.

currencies.csv - Contains data related to the supported currency
eufx-hist.csv - Conversion rates from year 2000 to 2022

## Setup
#### Requirements
- Java 11 (will run with OpenSDK 15 as well)
- Maven 3.x

#### Project
The project was generated through the Spring initializer [1] for Java
 11 with dev tools and Spring Web as dependencies. In order to build and 
 run it, you just need to click the green arrow in the Application class in your Intellij 
 CE IDE or run the following command from your project root und Linux or ios. 

````shell script
$ mvn spring-boot:run
````

## Available endpoints

````
GET /available-currencies - Returns the list of supported currencies

GET /historical-conversion-rate - Respond back with all the available euro fx exchange rate in the system

GET /conversion-rate?date=<date> - Returns the convuresion rate of the currencies for a particular date.

POST /conversion-rate - Accepts the source currency , source currency amount and date from the client and respond back with the conversion response.

GET /swagger-ui/ - API Documentation for the end consumers of the service

GET /health - Returns if the service is ready to take in requests. Useful in containerization of the application.
````
