package server.inputs;

public class UpdateObject {
    public int turn;
    public int[][] board;
    public UpdateObject(int t,int[][] b){
        turn=t;
        board=b;
    }
}
