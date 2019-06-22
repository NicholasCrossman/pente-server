package user;

public class Record {
    private int wins;
    private int losses;
    private int ties;

    /**
     * Default constructor for a new Record. Initializes wins, losses, and ties to 0.
     */
    public Record() {
        wins=0;
        losses=0;
        ties=0;
    }

    /**
     * Used to reconstitute a Record from a save file.
     * @param wins int - The number of wins a User has.
     * @param losses int - The number of losses a User has.
     * @param ties int - The number of ties a User has.
     */
    public Record(int wins, int losses, int ties) {
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public void increaseWins(){
        wins++;
    }

    public void increaseLosses(){
        losses++;
    }

    public void increaseTies(){
        ties++;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    @Override
    public String toString() {
        String s = "Record:\n\tWins: " + wins + "\n\tLosses: " + losses +
                "\n\tTies: " + ties;
        return s;
    }
    public String toData() {
        String s = wins+"_"+losses+"_"+ties;
        return s;
    }
}
