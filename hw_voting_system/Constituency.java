package hw_voting_system;

import java.util.ArrayList;
import java.util.List;

public class Constituency {
    private String name;
    private String region;
    private List<Voter> voters;
    private List<Vote> votes;
    private List<Candidate> candidates;

    public Constituency(String name, String region) {
        this.name = name;
        this.region = region;
        this.voters = new ArrayList<>();
        this.votes = new ArrayList<>();
        this.candidates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void addCandidateInConstituency(Candidate candidate) {
        candidates.add(candidate);
    }

    public boolean addVoter(Voter voter) {
        for (Voter v : voters) {
            if (v.getCnp().equals(voter.getCnp())) {
                return false;
            }
        }
        voters.add(voter);
        return true;
    }

    public List<Voter> getVoters() {
        return new ArrayList<>(voters);
    }

    public boolean castVote(String voterCnp, String candidateCnp) {
        for (Voter voter : voters) {
            if (voter.getCnp().equals(voterCnp)) {
                if (voter.hasVoted()) {
                    System.out.println("ERROR: The voter has already voted");
                    return false;
                }
                voter.setHasVoted(true);
                votes.add(new Vote(voterCnp, candidateCnp));
                return true;
            }
        }
        System.out.println("ERROR: The voter is not registered");
        return false;
    }

    public List<Vote> getVotes() {
        return new ArrayList<>(votes);
    }

    public int getTotalVotes() {
        return votes.size();
    }


    public List<String> getVoteReport() {
        List<Candidate> sortedCandidates = new ArrayList<>(candidates);
        sortedCandidates.sort((c1, c2) -> {
            int voteComparison = Integer.compare(c2.getVotes(), c1.getVotes());
            if (voteComparison == 0) {
                return c2.getCnp().compareTo(c1.getCnp());
            }
            return voteComparison;
        });

        List<String> report = new ArrayList<>();
        for (Candidate candidate : sortedCandidates) {
            String entry = candidate.getName() + " " + candidate.getCnp() + " - " + candidate.getVotes() + " votes";
            report.add(entry);
        }
        return report;
    }

    public List<String> getVoteReportInConstituency(Election election, String constituencyName) {
        List<Candidate> sortedCandidates = new ArrayList<>(candidates);
        sortedCandidates.sort((candidate1, candidate2) -> {
            int voteComparison = Integer.compare(
                    candidate2.getVotesInConstituency(election, constituencyName),
                    candidate1.getVotesInConstituency(election, constituencyName)
            );
            if (voteComparison == 0) {
                return candidate2.getCnp().compareTo(candidate1.getCnp());
            }
            return voteComparison;
        });
        List<String> report = new ArrayList<>();
        for (Candidate candidate : sortedCandidates) {
            String entry = candidate.getName() + " " + candidate.getCnp() + " - "
                    + candidate.getVotesInConstituency(election, constituencyName) + " votes";
            report.add(entry);
        }
        return report;
    }

    public List<String> getFrauds() {
        List<String> frauds = new ArrayList<>();
        for (Voter voter : voters) {
            if (voter.hasVoted() && voter.isBanned()) {
                frauds.add("In "+ voter.getConstituency().getName() + ": " + voter.getCnp() + " " + voter.getName());
            }
        }
        return frauds;
    }

    public Candidate getTopCandidate() {
        if (candidates.isEmpty()) {
            return null;
        }

        Candidate topCandidate = null;
        int maxVotes = -1;

        for (Candidate candidate : candidates) {
            int candidateVotes = candidate.getVotes();
            if (candidateVotes > maxVotes) {
                maxVotes = candidateVotes;
                topCandidate = candidate;
            }
        }
        return topCandidate;
    }

}