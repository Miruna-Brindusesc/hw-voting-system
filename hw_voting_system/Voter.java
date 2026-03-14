package hw_voting_system;

public class Voter {
    private String cnp;
    private int age;
    private boolean clumsy;
    private String name;
    private boolean hasVoted;
    private String candidateCnp;
    private Constituency constituency;
    private boolean banned;

    public Voter(String cnp, int age, boolean clumsy, String name) {
        this.cnp = cnp;
        this.age = age;
        this.clumsy = false;
        this.name = name;
        this.hasVoted = false;
        this.banned = false;
    }

    public String getCnp() {
        return cnp;
    }

    public String getCandidateCnp() {
        return candidateCnp;
    }

    public void setCandidateCnp(String candidateCnp) {
        this.candidateCnp = candidateCnp;
    }

    public int getAge() {
        return age;
    }

    public void setClumsy() {
        this.clumsy = true;
    }

    public boolean isClumsy() {
        return clumsy;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned() {
        this.banned = true;
    }

    public String getName() {
        return name;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }

    @Override
    public String toString() {
        return name + " (" + cnp + ", " + age + " years)";
    }
}