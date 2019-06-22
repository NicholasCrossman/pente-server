package game.rules;

import game.Board;
import game.Move;

public abstract class Rules {

    //fields
    public int size;
    public Board board;

    //methods
    /**
     * passes the board as a 2d array of ints
     * @return board as 2d array of ints
     */
    public int[][] getBoard(){
        return board.getBoardCells();
    }

    /**
     * all the checks look at lines of stones of the same player and counts them
     * @param player player that owns the piece
     * @param x x location of last move
     * @param y y location of last move
     * @return the count of pieces in a line
     */
    int checkUp(int player, int x, int y){
        boolean connected = true;
        int count = 0;
        for (int i = 1; i < 5; i++) {

            if(!connected||(y-i)<0){
                break;
            }
            else{
                if(board.cellState(x,y-i)==player){
                    count++;
                }
                else{
                    connected=false;
                }
            }
        }

        return count;
    }
    int checkDown(int player, int x, int y) {
        boolean connected = true;
        int count = 0;
        for (int i = 1; i < 5; i++) {

            if (!connected || (y + i) >= size) {//-
                break;
            } else {
                if (board.cellState(x, y + i) == player) {
                    count++;
                } else {
                    connected = false;
                }
            }
        }

        return count;
    }
    int checkLeft(int player, int x, int y){
        boolean connected = true;
        int count = 0;
        for (int i = 1; i < 5; i++) {

            if(!connected||(x-i)<0){
                break;
            }
            else{
                if(board.cellState(x-i,y)==player){
                    count++;
                }
                else{
                    connected=false;
                }
            }
        }

        return count;
    }
    int checkRight(int player, int x, int y){
        boolean connected = true;
        int count = 0;
        for (int i = 1; i < 5; i++) {

            if(!connected||(x+i)>=size){
                break;
            }
            else{
                if(board.cellState(x+i,y)==player){
                    count++;
                }
                else{
                    connected=false;
                }
            }
        }

        return count;
    }
    int checkUL(int player, int x, int y){
        boolean connected = true;
        int count = 0;
        for (int i = 1; i < 5; i++) {

            if(!connected||(y-i)<0||(x-i)<0){
                break;
            }
            else{
                if(board.cellState(x-i,y-i)==player){
                    count++;
                }
                else{
                    connected=false;
                }
            }
        }

        return count;
    }
    int checkUR(int player, int x, int y){
        boolean connected = true;
        int count = 0;
        for (int i = 1; i < 5; i++) {

            if(!connected||(y-i)<0||(x+i)>=size){
                break;
            }
            else{
                if(board.cellState(x+i,y-i)==player){
                    count++;
                }
                else{
                    connected=false;
                }
            }
        }

        return count;
    }
    int checkDL(int player, int x, int y){
        boolean connected = true;
        int count = 0;
        for (int i = 1; i < 5; i++) {

            if(!connected||(y+i)<=size||(x-i)<0){
                break;
            }
            else{
                if(board.cellState(x-i,y+i)==player){
                    count++;
                }
                else{
                    connected=false;
                }
            }
        }

        return count;
    }
    int checkDR(int player, int x, int y){
        boolean connected = true;
        int count = 0;
        for (int i = 1; i < 5; i++) {

            if(!connected||(y+i)>=size||(x+i)>=size){
                break;
            }
            else{
                if(board.cellState(x+i,y+i)==player){
                    count++;
                }
                else{
                    connected=false;
                }
            }
        }

        return count;
    }


    //abstract methods

    /**
     * checks if the move is legal
     * @param x x location of the move
     * @param y y location of the move
     * @return true if move is legal false if not
     */
    public abstract boolean isLegal(int x, int y);

    /**
     * Makes the move on the board after checking with isLegal
     * @param m the move to make
     */
    public abstract void makeMove(Move m);

    /**
     * checks if the move triggers a win
     * @param player the player who made the move
     * @param x the x location of last move
     * @param y the y location of last move
     * @return true if player wins false if not
     */
    public abstract boolean winState(int player, int x, int y);

}
