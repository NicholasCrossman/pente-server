package game;

/**board is initialized with -1 in all places. 
0 will signify the first player, while 1 will signify player 2
No numbers beyond -1, 0, 1 should be in the board.*/
public class Board {
    private int[][] boardCells;

    public Board(int size){
        boardCells = new int[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                boardCells[i][j] = -1;
            }
        }
    }
    public int[][] getBoardCells() {
        return boardCells;
    }
    public void setBoardCells(int[][] cells) {
        boardCells = cells;
    }

    public int cellState(int x,int y){
        return boardCells[x][y];
    }

    public void setCell(int player, int x, int y){
        boardCells[x][y] = player;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < boardCells.length; i++) {
            for(int j=0; j < boardCells.length; j++) {
                sb.append(cellState(i, j) + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
