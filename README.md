Coverage: 75.1%
# IMS version 1.0.0 - Tom Ayling - 22AprEnable1

This is an inventory management system that allows its users to create customers, items, and orders that have customers tied to them and items that relate to the order. 

Version 1.0.0 includes CRUD functionality on each table and interacts with the user through a command line interface.

Version 2.0.0 plans to include a users table so that users can come back to the inventory management system and pick up where they left of.
Version 2.0.0 will also have a GUI for the user to interact with that will make the software easier to use and more readable.

## Getting Started

To get a copy of this project up and running on your local machine for development and testing purposes simply clone this repository to your local machine through Git Bash and begin working with the source code though your IDE of choice.<br />
* See Prerequisites for information on how to get a functioning development environment running before working on the project.
* See deployment for notes on how to deploy the project on a live system.


### Prerequisites

To run and install this software, you will need:

The latest release of Git Bash that can be downloaded here:<br />
[GIT Download](https://git-scm.com/downloads)

Ensure to add GIT to your system environment variables.
An example of how to do that can be found here:<br />
[Add GIT to PATH](https://www.delftstack.com/howto/git/add-git-to-path-on-windows/)

The latest release of the Java Development Kit that can be downloaded here:<br />
[JDK 16 Download](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html)

Ensure to add java to your system environment variables. 
An example of how to do that can be found here:<br />
[Add Java to PATH](https://www.ibm.com/docs/en/b2b-integrator/5.2?topic=installation-setting-java-variables-in-windows)

A Java IDE of your choice - Eclipse is reccomended and can be downloaded here:<br />
[Eclipse Download](https://www.eclipse.org/downloads/).

The latest version of MySQL that can be dowloaded here:<br />
[MySQL Download](https://www.mysql.com/downloads/)

### Installing

A step by step series of examples that tell you how to get a development env running

Clone this git repository to your local machine<br />
Open eclipse and import the project from your local machine<br />
This will allow you to access and edit the source code

Before running the IMS, you will need to update the db.properties file and ensure that the password is the same as your MySQL password<br />
You will also need to run the SQL Schema found in src/main/resources before running the software for the first time.

### Running the program
Then run the Runner.java file through eclipse or open up the fat .jar (ims-1.0.0-jar-with-dependencies.jar)<br />
You will then be greeted by the IMS system, at which point you want to create some customers, items, and orders by selecting which table you want to work on, and selecting how you would like to operate on the table.<br />
For example, you could enter "CUSTOMER", then "CREATE", enter "Jeff" then "Bezos" and you will have a new customer in the system!<br />
To read the data simply select "CUSTOMER", then "READ" and you will get all entries in the system.<br />
Bear in mind that the system doesn't have any data within initially so you will have to create some entries first.<br />

When reading orders, you have the option to read all the orders in the system, or calculate the value of a single order.

The items and customers you want to add to your order must already exist before trying to create a new order (This will be fixed in version 2.0.0)


## Running the tests

To run the automated tests, right click the project folder in the project explorer on the left, and select `Run As` > `2 JUnit test`<br />
This will run all tests on the system.

### Unit Tests 

The unit tests test functionality of individual sections of the code to ensure they run as expected.

```
@Test
public void testCreate() {
  final Item created = new Item(2L, "table", 20d);
  assertEquals(created, DAO.create(created));
}
```
This tests whether the `create()` method work as expected on the `items` table in `ItemDAO.java`.<br />
We run this to make sure that the database connection is running as expected and the correct values are inserted into the table.

### Integration Tests 
The interation tests test whether the system works together as a whole and that the individual units work together.<br />
We us these to make sure that even if the unit tests are passing, the system works as a whole and there are no compatability issues between units.

```
@Test
public void testCreate() {
  final String name = "table";
  final Double value = 20d;
  final Item created = new Item(name, value);

  Mockito.when(utils.getString()).thenReturn(name);
  Mockito.when(utils.getDouble()).thenReturn(value);
  Mockito.when(dao.create(created)).thenReturn(created);

  assertEquals(created, controller.create());

  Mockito.verify(utils, Mockito.times(1)).getString();
  Mockito.verify(utils, Mockito.times(1)).getDouble();
  Mockito.verify(dao, Mockito.times(1)).create(created);
}
```
This tests the `create()` function on the `items` table in `ItemController.java`. <br />
It takes user input and sends it into the `create()` method in `ItemDAO.java`.<br />
This ensures that the user input is successfuly sent to the method and enters the correct values into the table through the DAO object.

## Deployment

Simply open up a command line in the root folder of the project.<br />
Then enter `mvn clean package` to remove any traces of the previous build and create a new build.<br />
Then change directory in the target folder (`cd target`) and run `java -jar ims<build version>-jar-with-dependencies.jar` to use the software.

## Supporting material

To request access to my Jira Board please send me an email at tjayling@yahoo.co.uk with your atlassian email and I'll add you to the users.<br />
Then follow the link below and you should be able to view the board.<br />
[Jira Board](https://tjayling.atlassian.net/jira/software/projects/IMS/boards/3/backlog)

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)
* **Tom Ayling** - *Version 1.0.0* - [tjayling](https://github.com/tjayling)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
