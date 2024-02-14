# Full Use Case: Select Query Type

## Goal in Context
Provide the user with the ability to select a specific type of query they wish to perform, ensuring a tailored and efficient report generation process within the CLI reporting application.

## Scope
CLI Application

## Level
Primary Task

## Preconditions
- The user has initiated the CLI application.
- The application is in a ready state to accept user input.

## Success End Condition
The user has successfully selected a query type, and the system is ready to accept further input based on the selected query type.

## Failed End Condition
The user exits the selection process without choosing a query type, or the system fails to process the user's selection correctly.

## Primary Actor
User

## Trigger
The user expresses the intention to generate a report by starting the query selection process within the application.

## Main Success Scenario
1. The system displays a list of available query types.
2. The user reviews the available options and selects a query type by entering the corresponding command.
3. The system acknowledges the selection and transitions to the parameter input stage for the selected query type.
