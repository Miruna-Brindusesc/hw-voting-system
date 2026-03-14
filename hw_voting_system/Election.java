package hw_voting_system;

import java.util.ArrayList;
import java.util.List;

public class Election {
    private String id;
    private String name;
    private ElectionStatus status;
    private List<Constituency> constituencies;
    private List<Candidate> candidates;
    private List<Vote> votes;

    public Election(String id, String name) {
        this.id = id;
        this.name = name;
        this.status = ElectionStatus.NOT_STARTED;
        this.constituencies = new ArrayList<>();
        this.candidates = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ElectionStatus getStatus() {
        return status;
    }

    public void setStatus(ElectionStatus status) {
        this.status = status;
    }

    public void addConstituency(Constituency constituency) {
        for (Constituency c : constituencies) {
            if (c.getName().equals(constituency.getName())) {
                return;
            }
        }
        constituencies.add(constituency);
    }

    public Constituency findConstituencyByName(String name) {
        for (Constituency c : constituencies) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public boolean removeConstituency(String name) {
        Constituency constituencyToRemove = findConstituencyByName(name);
        if (constituencyToRemove != null) {
            constituencyToRemove.getVoters().clear();
            constituencyToRemove.getVotes().clear();
            return constituencies.remove(constituencyToRemove);
        }
        return false;
    }

    public Constituency getConstituencyByName(String name) {
        for (Constituency constituency : constituencies) {
            if (constituency.getName().equalsIgnoreCase(name)) {
                return constituency;
            }
        }
        return null;
    }

    public List<Constituency> getConstituencies() {
        return new ArrayList<>(constituencies);
    }

    public boolean addCandidate(Candidate candidate) {
        for (Candidate c : candidates) {
            if (c.getCnp().equals(candidate.getCnp())) {
                return false; // Duplicate candidate
            }
        }
        candidates.add(candidate);
        return true;
    }

    public boolean removeCandidate(String cnp) {
        return candidates.removeIf(c -> c.getCnp().equals(cnp));
    }

    public Candidate findCandidateByCnp(String cnp) {
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }
        for (Candidate candidate : candidates) {
            if (candidate.getCnp().equals(cnp)) {
                return candidate;
            }
        }
        return null;
    }

    public List<Candidate> getCandidates() {
        return new ArrayList<>(candidates);
    }

    public List<Vote> getVotes() {
        votes.clear();
        for (Constituency constituency : constituencies) {
            votes.addAll(constituency.getVotes());
        }
        return new ArrayList<>(votes);
    }

    public int getTotalVotes() {
        getVotes();
        return votes.size();
    }

    public boolean addVoterToConstituency(String constituencyName, Voter voter) {
        if (!voter.isClumsy() && !voter.isBanned()){
            for (Constituency constituency : constituencies) {
                if (constituency.getName().equals(constituencyName)) {
                    return constituency.addVoter(voter);
                }
            }
        }
        return false;
    }

    public void banVoter(Voter voter) {
        voter.setBanned();
    }

    public List<Voter> getVotersByConstituency(String constituencyName) {
        for (Constituency constituency : constituencies) {
            if (constituency.getName().equals(constituencyName)) {
                return constituency.getVoters();
            }
        }
        return new ArrayList<>();
    }

    public Voter findVoterByCnpAndConstituency(String cnp, String constituencyName) {
        List<Voter> voters = getVotersByConstituency(constituencyName);
        if (voters == null || voters.isEmpty()) {
            return null;
        }
        for (Voter voter : voters) {
            if (voter.getCnp().equals(cnp)) {
                return voter;
            }
        }
        return null;
    }

    public Voter findVoterByCnp(String cnp){
        List<Voter> voters = getAllVoters();
        if (voters == null || voters.isEmpty()) {
            return null; // Return null if no voters
        }
        for (Voter voter : voters) {
            if (voter.getCnp().equals(cnp)) {
                return voter;
            }
        }
        return null;
    }

    public List<Voter> getAllVoters() {
        List<Voter> allVoters = new ArrayList<>();
        for (Constituency constituency : constituencies) {
            List <Voter> voters = constituency.getVoters();
            allVoters.addAll(voters);
        }
        return allVoters;
    }

    public boolean castVote(String constituencyName, String voterCnp, String candidateCnp) {
        for (Constituency constituency : constituencies) {
            if (constituency.getName().equals(constituencyName)) {
                return constituency.castVote(voterCnp, candidateCnp);
            }
        }
        return false;
    }

    public List<String> getFrauds() {
        List<String> frauds = new ArrayList<>();
        for (Constituency constituency : constituencies) {
            List<String> constituencyFrauds = constituency.getFrauds();
            if (!constituencyFrauds.isEmpty()) {
                frauds.addAll(constituencyFrauds);
            }
        }
        return frauds;
    }
}