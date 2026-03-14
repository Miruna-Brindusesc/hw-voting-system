# Homework: Voting System

A robust, Java-based console application designed to simulate, manage, and analyze electoral processes. The system handles everything from registering constituencies and candidates to casting votes, generating detailed statistical reports, and detecting electoral fraud.

## Key Features

* **Election Lifecycle Management**: Create, start, stop, and delete multiple independent elections.
* **Constituency & Region Tracking**: Organize the voting base geographically.
* **Candidate & Voter Registration**: Register participants with built-in validation (e.g., minimum age requirements, CNP length validation).
* **Secure Voting Mechanism**: 
    * Validates voter eligibility.
    * Prevents double voting.
    * Tracks and bans voters attempting to commit fraud.
* **Advanced Analytics & Reporting**:
    * Generate granular reports per constituency.
    * Generate consolidated national reports.
    * Calculate voting percentages and highlight winning candidates dynamically.
* **Fraud Detection**: Dedicated reporting for irregular voting attempts.

## Project Architecture

The application is built using core Object-Oriented Programming (OOP) principles and leverages Java 8 features (Streams, Lambdas) for efficient data processing.

### Main Components:
* `App.java`: The main entry point. Handles the CLI (Command Line Interface) and maps user input to internal services.
* `ElectionService.java`: The core business logic layer. Orchestrates the flow between elections, candidates, and voters.
* `Election.java`: Represents a single election instance, containing its status, constituencies, and candidates.
* `Constituency.java`: Models a specific voting region, managing its localized voters, candidates, and vote tallying.
* `Candidate.java` & `Voter.java`: Data models representing the participants. `Voter` includes flags for voting status, "clumsiness", and bans.
* `Vote.java`: A simple mapping object linking a voter's CNP to a candidate's CNP.

## Usage

### Prerequisites
* **Java Development Kit (JDK)**: Version 8 or higher.

### Compilation and Execution

1.  Compile the Java files:
    ```bash
    javac Tema1/*.java
    ```
2.  Run the application:
    ```bash
    java Tema1.App
    ```

## Commands

Once the application is running, it listens for numeric commands from `0` to `18` via the standard input:

| Command | Action |
| :--- | :--- |
| `0` | Create a new election |
| `1` | Start an election |
| `2` | Add a constituency |
| `3` | Remove a constituency |
| `4` | Add a candidate |
| `5` | Remove a candidate |
| `6` | Add a voter to a constituency |
| `7` | List all candidates |
| `8` | List voters in a specific constituency |
| `9` | Cast a vote |
| `10` | Stop an election |
| `11` | Generate constituency vote report |
| `12` | Generate national vote report |
| `13` | Detailed analysis (Constituency level) |
| `14` | Detailed analysis (National level) |
| `15` | Generate fraud report |
| `16` | Delete an election |
| `17` | List all existing elections |
| `18` | **Exit the application** |

## Fraud Prevention Logic

The system includes a verification process inside the `ElectionService` and `Constituency` classes. If a voter is marked as having already voted (`hasVoted()`) or attempts to vote while being banned (`isBanned()`), their vote is automatically annulled, and they are logged into the system's fraud registry.
