package game;

import game.rules.Rules;
import server.SavedGames;
import server.UserList;
import user.User;

import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.Date;
import java.util.LinkedList;

public class Game {
    private Boolean publicVisibility;
    private Rules rule;
    private ArrayList<String> users;
    private int turn; // 0 for Player 1's turn, 1 for Player 2's turn
    private Boolean completed;
    private String saveName;
    private LinkedList<Move> moveList = new LinkedList<Move>();

    /**
     * Creates a new Game. Sets the visibility to public or private.
     * Takes in a game type by specifying a Rules class. Has a list of
     * users where the first two are Players 1 and 2, and the rest are observers.
     * @param publicVisibility Boolean - If the game is public or private.
     * @param rule Rules - The set of rules the game is using.
     * @param users ArrayList<String> - The list of Users. Indexes 0 and 1
     *                 are Players 1 and 2. The rest are observers.
     */
    public Game(Boolean publicVisibility, Rules rule, ArrayList<String> users) {
        this.publicVisibility = publicVisibility;
        this.rule = rule;
        this.users = users;
        turn = 0; // Player 1 goes first
        completed = false;
        saveName = "";
    }

    /**
     * Allows a Player to make a Move. Modifies the Board and adds the
     * Move to the moveList.
     * @param player int - 0 for Player 1, 1 for Player 2.
     * @param x int - The x-coordinate.
     * @param y int - The y-coordinate.
     */
    public boolean makeMove(int player, int x, int y) {
        if(player != turn) {
            System.err.println("Turn error. Player " + (player + 1) + " made a " +
                    "move on Player " + (turn + 1) + "'s turn.");
        }
        if(rule.isLegal(x, y)) {
            Move m = new Move(player, x, y);
            moveList.add(m);
            rule.makeMove(m);
            if(rule.winState(player, x, y)){ // the game is won by the current player
                // if this move won, then the turn won't be given to the other player
                completed = true;
                turn = -2;
            }
            else {
                /* sets turn 1 if its current value is 0, and sets
                it to 0 if its current value is 1 */
                turn = 1 - turn;
            }
        } else {
            return false;
        }
        return true;
        //we should change this so that the makeMove call in Pente returns a true or false on win
    }

    /**
     * Returns the state of the Board from Rules as a 2-d array of ints.
     * @return int[][] - The Board state, with -1 being an empty space, 0
     *                  a piece belonging to Player 1, and 1 a piece belonging
     *                  to Player 2.
     */
    public int[][] getBoard() {
        return rule.getBoard();
    }

    public boolean isCompleted(){
        return completed;
    }

    public String getUser(int i){
        if (i >= users.size() || i < 0){
            return null;
        } else {
            return users.get(i);
        }
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String sName) {
        saveName = sName;
    }

    public boolean getPublicVisibility() {
        return publicVisibility;
    }

    public Rules getRule() {
        return rule;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public int getTurn() {
        return turn;
    }

    public LinkedList<Move> getMoveList() {
        return moveList;
    }

    /**
     * Returns the Date of the last move made.
     * @return Date - The Date of the last Move made.
     */
    public Date getLastMoveDate() {
        if(!moveList.isEmpty()) {
            Move m = moveList.getLast();
            return m.getDate();
        }
        else
            return null;
    }

    @Override
    public String toString() {
        return "\nGameName: " + saveName + "\n\tPublicVisibility: " + publicVisibility +
                "\n\tRule: " + rule + "\n\tUsers: " + users + "\n\tTurn: " + getUser(turn) +
                "\n\tCompleted: " + completed +
                "\n\tMoves: " + moveList;
    }
}
