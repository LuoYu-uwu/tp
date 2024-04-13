# GiT - Grocery in Time

## Introduction
Welcome to GiT, Grocery in Time, a Java application designed for efficient grocery management. This tool helps users monitor their groceries, including tracking expiration dates, managing inventory quantities, and setting alerts for low stock or soon-to-expire items.

## Getting Started

### Prerequisites
- Java JDK 11: Ensure you have Java Development Kit (JDK) 11 installed on your system. It is essential for running the application. You can download it from [Oracle's JDK download page](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

### Installation
1. **Download the latest release**
   - Download the `GiT.jar` file from the Releases section on the project's GitHub page or from the distribution email/website.

2. **Run the application**
   - Open a terminal or command prompt.
   - Navigate to the directory where `GiT.jar` is located.
   - Execute the following command to run the application:
     ```bash
     java -jar GiT.jar
     ```

## Features
- **Track Groceries**: Add, edit, and delete grocery items. Record details such as cost, category, expiration date, and quantity.
- **Alerts and Notifications**: Receive timely alerts for groceries that are about to expire and items that are running low on stock.
- **Search and Filter**: Use keywords to search through the inventory or filter items based on category or expiration date.
- **Reporting**: Generate reports on grocery usage, expenditure, and stock levels over time.

## How to Use
Upon launching GiT, you will be greeted with a simple text-based user interface. Here are some commands you can use:
- `add [item]`: Adds a new item to your inventory.
- `remove [item]`: Removes an item from the inventory.
- `update [item]`: Updates the details of an existing item.
- `list`: Lists all the items in your inventory.
- `help`: Displays help information about all the commands.

## Build automation using Gradle

* This project uses Gradle for build automation and dependency management. It includes a basic build script as well (i.e. the `build.gradle` file).
* If you are new to Gradle, refer to the [Gradle Tutorial at se-education.org/guides](https://se-education.org/guides/tutorials/gradle.html).

## Testing

### I/O redirection tests

* To run _I/O redirection_ tests (aka _Text UI tests_), navigate to the `text-ui-test` and run the `runtest(.bat/.sh)` script.

### JUnit tests

* A skeleton JUnit test (`src/test/java/seedu/duke/DukeTest.java`) is provided with this project template. 
* If you are new to JUnit, refer to the [JUnit Tutorial at se-education.org/guides](https://se-education.org/guides/tutorials/junit.html).

## Checkstyle

* A sample CheckStyle rule configuration is provided in this project.
* If you are new to Checkstyle, refer to the [Checkstyle Tutorial at se-education.org/guides](https://se-education.org/guides/tutorials/checkstyle.html).

## CI using GitHub Actions

The project uses [GitHub actions](https://github.com/features/actions) for CI. When you push a commit to this repo or PR against it, GitHub actions will run automatically to build and verify the code as updated by the commit/PR.

## Documentation

`/docs` folder contains a skeleton version of the project documentation.

## Contributing
Interested in contributing? Great! Please fork the project and submit a pull request with your proposed changes. Detailed instructions on setting up your development environment and the contribution guidelines can be found in the CONTRIBUTING.md file.

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
