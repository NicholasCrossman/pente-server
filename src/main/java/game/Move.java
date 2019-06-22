package game;

import java.util.Date;
import java.lang.StringBuilder;

public class Move {
    private Date date;
    private int player;
    private int locationX;
    private int locationY;

    /**
     * Constructor used to create a new Move. Sets the Player and coordinate fields,
     * and sets the Move's date field to the current Date.
     * @param player int - The Player who made the move. 0 for Player 1,
     *               1 for Player 2.
     * @param locationX int - The x-location of the Move.
     * @param locationY int - The y-location of the Move.
     */
    public Move(int player, int locationX, int locationY) {
        date = new Date();
        this.player = player;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    /**
     * Constructor used to reconstitute a Move after its data is loaded from a file.
     * @param d Date - The Date the move was made.
     * @param player int - The Player who made the move. 0 for Player 1,
     *               1 for Player 2.
     * @param locationX int - The x-location of the Move.
     * @param locationY int - The y-location of the Move.
     */
    public Move(Date d, int player, int locationX, int locationY) {
        date = d;
        this.player = player;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public Date getDate() {
        return date;
    }

    public int getPlayer() {
        return player;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    @Override
    public String toString() {
        return "\nMove: \n\tDate: " + date + "\n\tPlayer: " + player +
                "\n\tLocation: " + locationX + ", " + locationY;
    }

    public String toStringWithoutDate()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(player);
        sb.append(" ");
        sb.append(locationX);
        sb.append(" ");
        sb.append(locationY);
        sb.append("\n");
        return sb.toString();
    }
}
