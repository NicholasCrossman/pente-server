package game.rules;


import game.Board;
import game.Move;

public class Pente extends Rules {

    /**
     * constructor for the game Pente
     */
    public Pente(){
        size=19;
        board=new Board(size);
    }
    //TODO if tournament rules are used first players second move must be 3 spots away from center.
    /**
     * Constructor used to reconstitute a Pente object using data read from a file.
     * @param size int - The size of the board.
     * @param b Board - The Board attached to the Pente object.
     */
    public Pente(int size, Board b) {
        this.size = size;
        this.board = b;
    }

    /**
     * @see abstract Rules.isLegal
     */
    public boolean isLegal(int x,int y) {
        return board.cellState(x, y) == -1;
    }
    /**
     * removes captured pieces if they exist
     * @param player player that made the last move
     * @param x x location of the last move
     * @param y y location of the last move
     */
    private void captureCell(int player,int x, int y){
        int player2=0;
        if (player==0){
            player2=1;
        }
        if((checkUp(player2, x, y)==2) && (board.cellState(x,y-3)==player)){
            board.setCell(-1,x,y-1);
            board.setCell(-1,x,y-2);
        }
        if((checkDown(player2, x, y)==2) && (board.cellState(x,y+3)==player)){
            board.setCell(-1,x,y+1);
            board.setCell(-1,x,y+2);
        }
        if((checkLeft(player2, x, y)==2) && (board.cellState(x-3,y)==player)){
            board.setCell(-1,x-1,y);
            board.setCell(-1,x-2,y);
        }
        if((checkRight(player2, x, y)==2) && (board.cellState(x+3,y)==player)){
            board.setCell(-1,x+1,y);
            board.setCell(-1,x+2,y);
        }
        if((checkUL(player2, x, y)==2) && (board.cellState(x-3,y-3)==player)){
            board.setCell(-1,x-1,y-1);
            board.setCell(-1,x-2,y-2);
        }
        if((checkDR(player2, x, y)==2) && (board.cellState(x+3,y+3)==player)){
            board.setCell(-1,x+1,y+1);
            board.setCell(-1,x+2,y+2);
        }
        if((checkUR(player2, x, y)==2) && (board.cellState(x+3,y-3)==player)){
            board.setCell(-1,x+1,y-1);
            board.setCell(-1,x+2,y-2);
        }
        if((checkDL(player2, x, y)==2) && (board.cellState(x-3,y+3)==player)){
            board.setCell(-1,x-1,y+1);
            board.setCell(-1,x-2,y+2);
        }
    }

    /**
     * @see abstract Rules.makeMove
     */
    public void makeMove(Move m){
        if (isLegal(m.getLocationX(),m.getLocationY())){
            board.setCell(m.getPlayer(),m.getLocationX(),m.getLocationY());
        }
        captureCell(m.getPlayer(),m.getLocationX(),m.getLocationY());
    }
    /**
     * @see abstract Rules.winState
     */
    public boolean winState(int player, int x, int y) {
        int winCount = 4; //checks do not count the last move so 5-1
        int up = checkUp(player, x, y);
        int down = checkDown(player, x, y);
        int left = checkLeft(player, x, y);
        int right = checkRight(player, x, y);
        int UL = checkUL(player, x, y);
        int UR = checkUR(player, x, y);
        int DL = checkDL(player, x, y);
        int DR = checkDR(player, x, y);
        return up + down >= winCount ||
                left + right >= winCount ||
                UL + DR >= winCount ||
                UR + DL >= winCount;
    }

    /**
     * checks if the board is in a tie state.
     * ie does not have a free space (-1).
     * @return true if tied false if not
     */
    public boolean tieState(){
        int[][] state=board.getBoardCells();
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(state[i][j]==-1){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "\nSize: " + size + "\nBoard: " + board;
    }

}