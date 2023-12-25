# Almosafer-Test-Cases

# Almosafer Project Test Automation

This project contains automated test cases for the Almosafer website using Selenium WebDriver and TestNG.

## Table of Contents

- [Setup](#setup)
- [Test Cases](#test-cases)
- [Parallel Execution](#parallel-execution)
- [Parameters](#parameters)
- [Screenshots](#screenshots)
- [Dependencies](#dependencies)

## Setup

To run the test cases locally, follow these steps:

1. Clone this repository to your local machine.
2. Open the project in your preferred IDE (Eclipse, IntelliJ, etc.).
3. Make sure you have the necessary dependencies installed.
4. Configure the test suite by adjusting the testng.xml file.
5. Run the test suite.

## Test Cases

The project includes a set of test cases that cover different aspects of the Almosafer website, such as language selection, currency verification, contact number validation, and more. Each test case is annotated with a description, priority, and other relevant information.

## Parallel Execution

The test suite is configured to run in parallel across multiple browsers (Chrome, Firefox, Edge). The parallel execution is managed through TestNG's suite-level parallelization settings.

## Parameters

The test suite accepts parameters, such as the browser type, which can be configured in the testng.xml file. This allows for easy customization and execution of test cases on different browsers.

## Screenshots

The project includes functionality to capture screenshots during test execution. Screenshots are saved to the "screenshots" directory and provide visual documentation of test results.

## Dependencies

The project relies on the following dependencies:

- Selenium WebDriver
- TestNG
- WebDriverManager
- AShot (for screenshot capture)

Ensure that these dependencies are properly configured in your project.


