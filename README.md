Coverage: 75%
# IMS version 1.0.0 - Tom Ayling - 22AprEnable1

This is an inventory management system that allows its users to create customers, items, and orders that have customers tied to them and items that relate to the order. 
Version 1.0.0 includes CRUD functionality on each table and interacts with the user through a command line interface.

Version 2.0.0 plans to include a users table so that users can come back to the inventory management system and pick up where they left of.
Version 2.0.0 will also have a GUI for the user to interact with that will make the software easier to use and more readable.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

```
Simply clone this repository to your local machine through Git Bash to begin working with the source code.
See Prerequisites for information on how to get a functioning development environment running before trying to work with the project.
```

### Prerequisites

To run and install this software, you will need:

```
The latest release of Git Bash that can be downloaded here:
https://git-scm.com/downloads
Ensure to add GIT to youe system environment variables.
An example of how to do this can be found here:
https://www.delftstack.com/howto/git/add-git-to-path-on-windows/

The latest release of the Java Development Kit that can be downloaded here:
https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html
Ensure to add java to your system environment variables. 
An example of how to do this can be found here:
https://www.ibm.com/docs/en/b2b-integrator/5.2?topic=installation-setting-java-variables-in-windows

A java IDE of your choice - Eclise reccomended and can be downloaded here:
https://www.eclipse.org/downloads/

The latest version of MySQL that can be dowloaded here:
https://www.mysql.com/downloads/
```

### Installing

A step by step series of examples that tell you how to get a development env running

```
Clone this git repository to your local machine
Open eclipse and import the project from your local machine
This will allow you to access and edit the source code

Before running the IMS, you will need to update the db.properties file and ensure that the password is the same as your MySQL password
You will also need to run the SQL Schema found in src/main/resources before running the software for the first time.
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system. Break down into which tests and what they do

### Unit Tests 

Explain what these tests test, why and how to run them

```
Give an example
```

### Integration Tests 
Explain what these tests test, why and how to run them

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
