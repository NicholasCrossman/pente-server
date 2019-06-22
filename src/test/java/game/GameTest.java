package game;
import game.rules.*;
import server.SavedGames;
import user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GameTest {
    private Game pente;
    private ArrayList<String> users;
    private Board exampleBoard;
    private SavedGames sg;
    @Before
    public void setUp() throws Exception {
        users = new ArrayList<String>();
        users.add("player1");
        users.add("player2");
        sg = new SavedGames();
        pente = new Game(true, new Pente(), users);
        exampleBoard = new Board(19);
    }

    @Test
    public void makeMoveTest() {
        pente.makeMove(0,0,0);
        exampleBoard.setCell(0,0,0);
        Assert.assertArrayEquals(pente.getBoard(), exampleBoard.getBoardCells());
    }

    @Test
    public void getUser() {
        Assert.assertEquals(pente.getUser(1),users.get(1));
    }
    @Test
    public void isCompletedTest(){
        pente.makeMove(0,0,0); // Player 1 moves to 0, 0
        pente.makeMove(1,5,7);
        pente.makeMove(0,0,1);// Player 1 moves to 0, 1
        pente.makeMove(1,2,3);
        pente.makeMove(0,0,2); // Player 1 moves to 0, 2
        pente.makeMove(1,3,8);
        pente.makeMove(0,0,3); // Player 1 moves to 0, 3
        pente.makeMove(1,2,8);
        pente.makeMove(0,0,4);// Player 1 moves to 0, 4
        Assert.assertTrue(pente.isCompleted());
    }
    @Test
    public void isCompletedTest2(){
        pente.makeMove(0,0,0); // Player 1 moves to 0, 0
        pente.makeMove(1,5,7);
        pente.makeMove(0,0,1);// Player 1 moves to 0, 1
        pente.makeMove(1,2,3);
        pente.makeMove(0,0,2); // Player 1 moves to 0, 2
        pente.makeMove(1,3,8);
        pente.makeMove(0,0,3); // Player 1 moves to 0, 3
        Assert.assertFalse(pente.isCompleted());
    }
}