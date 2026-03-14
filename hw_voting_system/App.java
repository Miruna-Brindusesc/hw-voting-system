package hw_voting_system;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.text.*;

public class App {
    private Scanner scanner;
    private List<Election> elections;
    private ElectionService electionService;

    public App(InputStream input) {
        this.scanner = new Scanner(input);
        this.elections = new ArrayList<>();
        this.electionService = new ElectionService();
    }

    public void run() {
        while (true) {
            String command = scanner.nextLine();

            if (command.equals("18")) {
                break;
            }

            switch (command) {
                case "0": // Create election
                    System.out.println("Enter election ID and name:");
                    String[] createInput = scanner.nextLine().split(" ", 2);
                    electionService.createElection(createInput[0], createInput[1]);
                    break;

                case "1": // Start election
                    System.out.println("Enter election ID:");
                    String startId = scanner.nextLine();
                    electionService.startElection(startId);
                    break;

                case "2": // Add constituency
                    System.out.println("Enter election ID, constituency name, and region:");
                    String[] addCircInput = scanner.nextLine().split(" ", 3);
                    electionService.addConstituency(addCircInput[0], addCircInput[1], addCircInput[2]);
                    break;

                case "3": // Remove constituency
                    System.out.println("Enter election ID and constituency name:");
                    String[] removeCircInput = scanner.nextLine().split(" ", 2);
                    electionService.removeConstituency(removeCircInput[0], removeCircInput[1]);
                    break;

                case "4": // Add candidate to election
                    System.out.println("Enter election ID, CNP, age, and candidate name:");
                    String[] addCandidateInput = scanner.nextLine().split(" ", 4);
                    electionService.addCandidate(addCandidateInput[0], addCandidateInput[1],
                            Integer.parseInt(addCandidateInput[2]), addCandidateInput[3]);
                    break;

                case "5": // Remove candidate from election
                    System.out.println("Enter election ID and candidate CNP:");
                    String[] removeCandidateInput = scanner.nextLine().split(" ", 2);
                    electionService.removeCandidate(removeCandidateInput[0], removeCandidateInput[1]);
                    break;

                case "6": // Add voter to constituency
                    System.out.println("Enter election ID, constituency name, CNP, age, clumsy (yes/no), and voter name:");
                    String[] addVoterInput = scanner.nextLine().split(" ", 6);
                    electionService.addVoter(addVoterInput[0], addVoterInput[1], addVoterInput[2],
                            Integer.parseInt(addVoterInput[3]), addVoterInput[4].equals("yes"), addVoterInput[5]);
                    break;

                case "7": // List candidates in election
                    System.out.println("Enter election ID:");
                    String listCandidatesId = scanner.nextLine();
                    electionService.listCandidates(listCandidatesId);
                    break;

                case "8": // List voters in a constituency
                    System.out.println("Enter election ID and constituency name:");
                    String[] listVotersInput = scanner.nextLine().split(" ", 2);
                    electionService.listVoters(listVotersInput[0], listVotersInput[1]);
                    break;

                case "9": // Voting
                    System.out.println("Enter election ID, constituency name, voter CNP, and candidate CNP:");
                    String[] voteInput = scanner.nextLine().split(" ", 4);
                    electionService.vote(voteInput[0], voteInput[1], voteInput[2], voteInput[3]);
                    break;

                case "10": // Stop election
                    System.out.println("Enter election ID:");
                    String stopId = scanner.nextLine();
                    electionService.stopElection(stopId);
                    break;

                case "11": // Create constituency vote report
                    System.out.println("Enter election ID and constituency name:");
                    String[] reportCircInput = scanner.nextLine().split(" ", 2);
                    electionService.generateConstituencyReport(reportCircInput[0], reportCircInput[1]);
                    break;

                case "12": // Create national vote report
                    System.out.println("Enter election ID:");
                    String nationalReportId = scanner.nextLine();
                    electionService.generateNationalReport(nationalReportId);
                    break;

                case "13": // Detailed constituency analysis
                    System.out.println("Enter election ID and constituency name:");
                    String[] detailedAnalysisInput = scanner.nextLine().split(" ", 2);
                    electionService.analyzeConstituency(detailedAnalysisInput[0], detailedAnalysisInput[1]);
                    break;

                case "14": // Detailed national analysis
                    System.out.println("Enter election ID:");
                    String nationalAnalysisId = scanner.nextLine();
                    electionService.analyzeNational(nationalAnalysisId);
                    break;

                case "15": // Fraud reports
                    System.out.println("Enter election ID:");
                    String fraudReportId = scanner.nextLine();
                    electionService.reportFrauds(fraudReportId);
                    break;

                case "16": // Delete election
                    System.out.println("Enter election ID:");
                    String deleteId = scanner.nextLine();
                    electionService.deleteElection(deleteId);
                    break;

                case "17": // List elections
                   electionService.listElections();
                    break;

                default:
                    System.out.println("Unknown command. Try again.");
            }
        }

    }

    public static void main(String[] args) {
        App app = new App(System.in);
        app.run();
    }
}