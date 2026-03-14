package hw_voting_system;

public class Candidate {
    private String cnp;
    private int age;
    private String name;
    private int votes;

    public Candidate(String cnp, int age, String name) {
        this.cnp = cnp;
        this.age = age;
        this.name = name;
    }

    public String getCnp() {
        return cnp;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    public int getVotesInConstituency( Election election, String constituencyName) {
        int votes=0;
        Constituency targetConstituency = null;
        for (Constituency c : election.getConstituencies()) {
            if (c.getName().equals(constituencyName)) {
                targetConstituency = c;
                break;
            }
        }
        for (Voter v : targetConstituency.getVoters()) {
            if (v.getCandidateCnp().equals(cnp)) {
                votes++;
            }
        }
        return votes;
    }

    public int getVotesInRegion(Election election, String regionName) {
        int votes = 0;

        for (Constituency constituency : election.getConstituencies()) {
            if (constituency.getRegion().equalsIgnoreCase(regionName)) {
                for (Voter voter : constituency.getVoters()) {
                    if (voter.getCandidateCnp().equals(this.cnp)) {
                        votes++;
                    }
                }
            }
        }

        return votes;
    }

    public void addVote() {
        this.votes++;
    }

    @Override
    public String toString() {
        return name + " (" + cnp + ", " + age + " years)";
    }
}