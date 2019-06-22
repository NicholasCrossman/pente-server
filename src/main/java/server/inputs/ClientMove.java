package server.inputs;

public class ClientMove {
    public int x;
    public int y;
    public int player;
    public ClientMove(int x, int y, int player){
        this.x = x;
        this.y = y;
        this.player = player;
    }
}
