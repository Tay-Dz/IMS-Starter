Coverage: 73.3%
# Inventory Management System

An Inventory Management System program written in Java. The program inlcludes CRUD functionality for customer, item and order entites within an SQL database.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them
* Java 8
* MySQL 5.7


### Installing

First download the files and open up the project in eclipse or any other java IDE. 
Ensure MySQL is downloaded and in db.properties ensure db.url, db.user and db.password are correct.


## Running the tests

Explain how to run the automated tests for this system. Break down into which tests and what they do

### Unit Tests 

JUnit tests are used to test individual components of each class.
To run the test open up the test class file in your IDE and select 'Run as JUnit Test'.

### Coverage Test

Right click the project folder in the 'Project Explorer' and select 'Coverage as JUnit Test'.


## Deployment

Run the program in the command line with:
``
java -jar ims-0.0.1-jar-with-dependencies.jar
``

If you update the program, rebuild the program in the commandline with:
``
maven clean package
``
Then run the program in the command line with:
``
java -jar target/ims-0.0.1-jar-with-dependencies.jar
``

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)
* **Tay Dzonu** - *Completed work from IMS starter* - [Tay-Dzonu-QA](https://github.com/Tay-Dzonu-QA)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 
