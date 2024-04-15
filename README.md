# GiT - Grocery in Time

```
   ______   _  _________
 .' ___  | (_)|  _   _  |
/ .'   \_| __ |_/ | | \_|
| |   ____[  |    | |
\ `.___]  || |   _| |_
 `._____.'[___] |_____|
 ```


## Introduction
Welcome to GiT, Grocery in Time, a Java application designed for efficient grocery management. This tool helps users monitor their groceries, including tracking expiration dates, managing inventory quantities, and setting alerts for low stock or soon-to-expire items.

## Table of Contents
- [Getting Started](#Getting-started)
- [Features](#features)
    - [Common Commands](#common-commands)
    - [Grocery Management](#grocery-management)
    - [Calories Management](#calories-management)
    - [Profile Management](#profile-management)
    - [Recipe Management](#recipe-management)
- [Data Management](#data-management)
- [Command Summary](#command-summary)
- [Documentation](#documentation)
- [Contributing](#contributing)
- [License](#license)

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
### Inovative Four Mode Application
- **Groceries Management**: Add, edit, and delete grocery items with detailed commands. Manage your inventory by setting categories, amounts, expiration dates, and storage locations. Examples include `add GROCERY`, `del GROCERY`, and `edit GROCERY`.
- **Calorie Management**: Track calorie intake by logging food consumption and viewing total calories. Commands like `eat FOOD` and `view` help maintain dietary goals.
- **Recipe Management**: Add, view, and manage recipes. Store detailed recipes including ingredients and cooking steps, and find recipes using keywords with commands such as `add RECIPE`, `view RECIPE`, and `find KEYWORD`.
- **Profile Management**: Customize user profiles to support calorie management based on individual dietary needs. Update personal information and view user details with commands like `update` and `view`.

## How to Use
Upon launching GiT, you will be greeted with a simple text-based user interface. 

### Common Commands
- **Switch Mode**: `switch`
    - Switches the application between different modes (grocery, profile, calories, recipe).
- **Exit**: `exit`
    - Closes the application.

### Grocery Management
Manage your grocery items effectively using these commands:
- **Add Grocery**: `add GROCERY`
- **Edit Grocery**: Multiple commands to set category, amount, location, etc.
- **Delete Grocery**: `del GROCERY`
- **List Groceries**: Multiple listing options based on category, price, expiration, etc.
- **Find Grocery**: `find KEYWORD`
- **Grocery Details**: `view GROCERY`

### Calories Management
Track and manage your daily calorie intake:
- **Add Food Consumption**: `eat FOOD`
- **View Calorie Intake**: `view`


### Profile Management
Manage user profile for personalized calorie tracking:
- **Update Profile**: `update`
- **View Profile**: `view`


### Recipe Management
Store and manage recipes:
- **Add Recipe**: `add`
- **View Recipes**: Multiple commands to view, list, find, and edit recipes.
- **Delete Recipe**: `delete RECIPE`


## Data Management
GiT automatically saves your data in the `/data` folder located in the same directory as the JAR file. The data includes separate files for groceries, calories, profile, and recipes.

### Caution
Modifying data files manually can corrupt them. Always back up your data before making manual changes.

## Command Summary

| Command | Description | Format |
| --- | --- | --- |
| **Common** | | |
| Switch | Switch application mode | `switch` |
| Exit | Close the application | `exit` |
| **Grocery Management** | | |
| Add | Add a grocery item | `add GROCERY` |
| Delete | Delete a grocery item | `del GROCERY` |
| List | List groceries | Multiple formats |
| **Calories Management** | | |
| Eat | Log food consumption | `eat FOOD` |
| View | View calorie intake | `view` |
| **Profile Management** | | |
| Update | Update user profile | `update` |
| View | View user profile | `view` |
| **Recipe Management** | | |
| Add | Add a new recipe | `add RECIPE` |
| Delete | Delete a recipe | `delete RECIPE` |


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
