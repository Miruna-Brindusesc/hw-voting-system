package hw_voting_system;

public class Vote {
    private String voterCnp;
    private String candidateCnp;

    public Vote(String voterCnp, String candidateCnp) {
        this.voterCnp = voterCnp;
        this.candidateCnp = candidateCnp;
    }

    public String getVoterCnp() {
        return voterCnp;
    }

    public String getCandidateCnp() {
        return candidateCnp;
    }
}