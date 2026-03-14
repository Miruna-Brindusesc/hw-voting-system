package hw_voting_system;

import java.util.*;
import java.util.stream.Collectors;


public class ElectionService {
    private List<Election> elections;
    public ElectionService() {
        this.elections = new ArrayList<>();
    }
    
    public void createElection(String id, String name) {
        for (Election election : elections) {
            if (election.getId().equals(id)) {
                System.out.println("ERROR: An election with ID " + id + " already exists");
                return;
            }
        }
        elections.add(new Election(id, name));
        System.out.println("Success. Election " + name + " was created");
    }
    
    public void startElection(String id) {
        Election election = findElectionById(id);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        if (election.getStatus() != ElectionStatus.NOT_STARTED) {
            System.out.println("ERROR: The election has already started");
            return;
        }
        election.setStatus(ElectionStatus.IN_PROGRESS);
        System.out.println("Success. Election " + election.getName() + " has started");
    }

    public Election findElectionById(String id) {
        return elections.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    public void addConstituency(String id, String name, String region) {
        Election election = findElectionById(id);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        if (election.getStatus() != ElectionStatus.IN_PROGRESS) {
            System.out.println("ERROR: It is not the voting period");
            return;
        }
        if (election.findConstituencyByName(name) != null) {
            System.out.println("ERROR: A constituency with the name " + name + " already exists");
            return;
        }
        Constituency constituency = new Constituency(name, region);
        election.addConstituency(constituency);
        System.out.println("Success. Constituency " + name + " was added");
    }

    public void removeConstituency(String id, String name) {
        Election election = findElectionById(id);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        if (election.getStatus() != ElectionStatus.IN_PROGRESS) {
            System.out.println("ERROR: It is not the voting period");
            return;
        }
        if (election.removeConstituency(name)) {
            System.out.println("Success. Constituency " + name + " was removed");
        } else {
            System.out.println("ERROR: There is no constituency with the name " + name);
        }
    }
    
    public void addCandidate(String id, String cnp, int age, String name) {
        if (age < 35) {
            System.out.println("ERROR: Invalid age");
            return;
        }
        if (cnp.length() != 13) {
            System.out.println("ERROR: Invalid CNP");
            return;
        }
        Election election = findElectionById(id);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        if (election.getStatus() != ElectionStatus.IN_PROGRESS) {
            System.out.println("ERROR: It is not the voting period");
            return;
        }
        Candidate candidate = new Candidate(cnp, age, name);
        if (election.addCandidate(candidate)) {
            System.out.println("Candidate " + name + " was added");
        } else {
            System.out.println("ERROR: Candidate " + name + " already has the same CNP");
        }
    }
    
    public void removeCandidate(String id, String cnp) {
        Election election = findElectionById(id);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        if (election.getStatus() != ElectionStatus.IN_PROGRESS) {
            System.out.println("ERROR: It is not the voting period");
            return;
        }
        Candidate candidate = election.findCandidateByCnp(cnp);
        if (election.removeCandidate(cnp)) {
            System.out.println("Candidate "+ candidate.getName() + " was removed");
        } else {
            System.out.println("ERROR: There is no candidate with the CNP " + cnp);
        }
    }
    
    public void addVoter(String id, String constituencyName, String cnp, int age, boolean clumsy, String name) {
        if (age < 18) {
            System.out.println("ERROR: Invalid age");
            return;
        }
        if (cnp.length() != 13) {
            System.out.println("ERROR: Invalid CNP");
            return;
        }
        Election election = findElectionById(id);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        Constituency targetConstituency = null;
        for (Constituency c : election.getConstituencies()) {
            if (c.getName().equals(constituencyName)) {
                targetConstituency = c;
                break;
            }
        }
        if (targetConstituency == null) {
            System.out.println("ERROR: There is no constituency with the name " + constituencyName);
            return;
        }
        Voter voter = new Voter(cnp, age, clumsy, name);
        if (election.addVoterToConstituency(constituencyName, voter)) {
            System.out.println("Voter " + name + " was added");
            voter.setConstituency(targetConstituency);
        } else {
            voter.setBanned();
            System.out.println("ERROR: Voter " + name + " already has the same CNP");
        }
    }
    
    public void listCandidates(String id) {
        Election election = findElectionById(id);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        if (election.getStatus() != ElectionStatus.IN_PROGRESS) {
            System.out.println("ERROR: The election has not started yet");
            return;
        }
        List<Candidate> candidates = election.getCandidates();
        if (candidates.isEmpty()) {
            System.out.println("EMPTY: There are no candidates");
        } else {
            System.out.println("Candidates:");
            candidates.stream()
                    .sorted(Comparator.comparing(Candidate::getCnp)) // Ascending order by CNP
                    .forEach(candidate ->
                            System.out.println(candidate.getName() + " " + candidate.getCnp() + " " + candidate.getAge())
                    );
        }
    }
    
    public void listVoters(String id, String constituencyName) {
        Election election = findElectionById(id);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        if (election.getStatus() != ElectionStatus.IN_PROGRESS) {
            System.out.println("ERROR: The election has not started yet");
            return;
        }
        Constituency targetConstituency = null;
        for (Constituency c : election.getConstituencies()) {
            if (c.getName().equals(constituencyName)) {
                targetConstituency = c;
                break;
            }
        }
        if (targetConstituency == null) {
            System.out.println("ERROR: There is no constituency with the name " + constituencyName);
            return;
        }
        List<Voter> voters = election.getVotersByConstituency(constituencyName);
        if (voters.isEmpty()) {
            System.out.println("EMPTY: There are no voters in " + constituencyName);
        } else {
            System.out.println("Voters in " + constituencyName + ":");
            voters.stream()
                    .sorted(Comparator.comparing(Voter::getCnp)) // Ascending order by CNP
                    .forEach(voter ->
                            System.out.println(voter.getName() + " " + voter.getCnp() + " " + voter.getAge())
                    );
        }
    }
    
    public void vote(String id, String constituencyName, String voterCnp, String candidateCnp) {
        Election election = findElectionById(id);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        if (election.getStatus() != ElectionStatus.IN_PROGRESS) {
            System.out.println("ERROR: It is not the voting period");
            return;
        }
        Candidate candidate = election.findCandidateByCnp(candidateCnp);
        Voter voterCheck = election.findVoterByCnpAndConstituency(voterCnp, constituencyName);
        Voter voter = election.findVoterByCnp(voterCnp);
        Constituency targetConstituency = null;
        for (Constituency c : election.getConstituencies()) {
            if (c.getName().equals(constituencyName)) {
                targetConstituency = c;
                break;
            }
        }
        if (targetConstituency == null) {
            System.out.println("ERROR: There is no constituency with the name " + constituencyName);
            return;
        }
        if (voter.hasVoted() == true || voterCheck != voter){
            System.out.println("FRAUD: The voter with CNP " +voter.getCnp() + " tried to commit fraud. The vote was cancelled");
            voter.setClumsy();
            voter.setBanned();
            return;
        }
        if (candidate == null) {
            System.out.println("ERROR: There is no candidate with the CNP " + candidateCnp);
            return;
        }
        if (voter == null) {
            System.out.println("ERROR: There is no voter with the CNP " + voterCnp);
            return;
        }

        if (voter.isClumsy()) {
            voter.setBanned();
            System.out.println(voter.getName() + " voted for " + candidate.getName());
        }

        if (election.castVote(constituencyName, voterCnp, candidateCnp) && !voter.isClumsy()) {
            voter.setHasVoted(true);
            candidate.addVote();
            targetConstituency.addCandidateInConstituency(candidate);
            voter.setCandidateCnp(candidateCnp);
            System.out.println(voter.getName() + " voted for " + candidate.getName());
        } else {
            System.out.println("ERROR: The vote was not registered.");
        }
    }

    public void stopElection(String id) {
        Election election = findElectionById(id);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        if (election.getStatus() != ElectionStatus.IN_PROGRESS) {
            System.out.println("ERROR: It is not the voting period");
            return;
        }
        election.setStatus(ElectionStatus.FINISHED);
        System.out.println("Election " + election.getName() + " has ended");
    }

    public void deleteElection(String id) {
        Election election = findElectionById(id);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        elections.remove(election);
        System.out.println("Election " + election.getName() + " was deleted.");
    }

    public void generateConstituencyReport(String electionId, String constituencyName) {
        Election election = findElectionById(electionId);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }

       if (election.getStatus() != ElectionStatus.IN_PROGRESS) {
           System.out.println("ERROR: Voting has not ended yet");
           return;
       }
        Constituency constituency = election.getConstituencyByName(constituencyName);
        if (constituency == null) {
            System.out.println("ERROR: There is no constituency with the name " + constituencyName);
            return;
        }
        List<String> report = constituency.getVoteReportInConstituency(election, constituencyName);
        if (report.isEmpty()) {
            System.out.println("EMPTY: People are not exercising their right to vote in " + constituencyName);
        } else {
            System.out.println("Vote report " + constituencyName + ":");
            report.forEach(System.out::println);
        }
    }

    public void generateNationalReport(String electionId) {
        Election election = findElectionById(electionId);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        List<String> report = new ArrayList<>();
        for (Constituency constituency : election.getConstituencies()) {
            report.addAll(constituency.getVoteReport());
        }
        if (report.isEmpty()) {
            System.out.println("EMPTY: People are not exercising their right to vote in Romania");
        } else {
            System.out.println("Vote report Romania:");
            report=report.stream().distinct().collect(Collectors.toList());
            report.forEach(System.out::println);
        }
    }


    public void analyzeConstituency(String electionId, String constituencyName) {
        Election election = findElectionById(electionId);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        Constituency constituency = election.getConstituencyByName(constituencyName);
        if (constituency == null) {
            System.out.println("ERROR: There is no constituency " + constituencyName);
            return;
        }
        int totalVotesConstituency = constituency.getTotalVotes();
        if (totalVotesConstituency == 0) {
            System.out.println("EMPTY: People are not exercising their right to vote in " + constituencyName);
            return;
        }
        int totalVotes = election.getTotalVotes();
        if (totalVotes == 0) {
            System.out.println("EMPTY: There are no votes");
            return;
        }
        Candidate topCandidate = constituency.getTopCandidate();
        if (topCandidate == null) {
            System.out.println("ERROR: There are no candidates in " + constituencyName);
            return;
        }
        int candidateVotes = topCandidate.getVotesInConstituency(election, constituencyName);
        double percentageConstituency = (double) candidateVotes / totalVotesConstituency * 100;
        double percentageNational = (double) candidateVotes / totalVotes * 100;
        System.out.println("In " + constituencyName + " there were " + candidateVotes + " votes out of " + totalVotes+ ". That is " + (int) percentageNational + "%. The most votes were gathered by "
                + topCandidate.getCnp() + " " + topCandidate.getName() + ". These constitute " + (int) percentageConstituency + "% of the constituency's votes.");
    }

    public void analyzeNational(String electionId) {
        Election election = findElectionById(electionId);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }
        int totalNationalVotes = election.getTotalVotes();
        if (totalNationalVotes == 0) {
            System.out.println("EMPTY: People are not exercising their right to vote in Romania");
            return;
        }
        System.out.println("In Romania there were " + totalNationalVotes + " votes.");
        Set<String> regions = new HashSet<>();
        for (Constituency currentConstituency : election.getConstituencies()) {
            regions.add(currentConstituency.getRegion());
        }
        List<String> sortedRegions = new ArrayList<>(regions);
        Collections.sort(sortedRegions);
        List<String> analysisResults = new ArrayList<>();
        for (String currentRegion : sortedRegions) {
            int regionTotalVotes = 0;
            Candidate regionTopCandidate = null;
            for (Candidate candidate : election.getCandidates()) {
                int candidateVotesInRegion = candidate.getVotesInRegion(election, currentRegion);
                regionTotalVotes += candidateVotesInRegion;
                if (regionTopCandidate == null || candidateVotesInRegion > regionTopCandidate.getVotesInRegion(election, currentRegion)) {
                    regionTopCandidate = candidate;
                }
            }
            double percentageNational = (double) regionTotalVotes / totalNationalVotes * 100;
            double percentageRegion = regionTopCandidate != null
                    ? (double) regionTopCandidate.getVotesInRegion(election, currentRegion) / regionTotalVotes * 100
                    : 0;
            String result = "In " + currentRegion + " there were " + regionTotalVotes + " votes out of " + totalNationalVotes + ". That is " + (int) percentageNational + "%. The most votes were gathered by "
                    + regionTopCandidate.getCnp() + " " + regionTopCandidate.getName() + ". These constitute " + (int) percentageRegion + "% of the region's votes.";
            analysisResults.add(result);
        }
        for (String result : analysisResults) {
            System.out.println(result);
        }
    }


    public void reportFrauds(String electionId) {
        Election election = findElectionById(electionId);
        if (election == null) {
            System.out.println("ERROR: There is no election with this ID");
            return;
        }

        List<String> frauds = election.getFrauds();
        if (frauds.isEmpty()) {
            System.out.println("EMPTY: Romanians are honest");
        } else {
            System.out.println("Committed frauds:");
            frauds.forEach(System.out::println);
        }
    }

    public void listElections() {
        if (elections.isEmpty()) {
            System.out.println("EMPTY: There are no available elections.");
            return;
        }
        System.out.println("Elections:");
        for (Election election : elections) {
            System.out.println(election.getId() + " " + election.getName());
        }
    }
}