package server;

import game.Game;
import game.rules.Pente;
import user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class SavedGamesTest {

    User u1;
    User u2;
    UserList userList = new UserList();
    ArrayList<String> users;
    SavedGames sg;
    Game g;
    String saveName;

    @Before
    public void setUp() throws Exception {
        users = new ArrayList<String>();
        userList.createUser("Mark", "pass1");
        userList.createUser("John", "pass2");
        u1 = userList.getUserByName("Mark");
        u2 = userList.getUserByName("John");
        users.add(u1.getUserName());
        users.add(u2.getUserName());
        sg = new SavedGames();
        g = new Game(true, new Pente(), users);
        g.makeMove(0, 1, 2);
        g.makeMove(1, 2, 1);
        sg.addGame(g);
        saveName = g.getSaveName();
    }

    @Test
    public void addLoadGameTest() {
        SavedGames savedGames = new SavedGames();
        savedGames.addGame(g);
        String saveName = g.getSaveName();
        Game test = savedGames.loadGame(saveName);
        Assert.assertEquals(test, g);
    }

    @Test
    public void loadGameTest2() {
        Game test = sg.loadGame("invalidName");
        // if the Game doesn't exist, it should return null
        Assert.assertNull(test);
    }

    @Test
    public void addGameTest() {
        // if the Game is added successfully, addGame() should return True
        Game testGame = new Game(true, new Pente(), users);
        boolean test = sg.addGame(testGame);
        Assert.assertTrue(test);
    }

    @Test
    public void addGameTest2() {
        // if the Game being added is null, it should return false
        boolean test = sg.addGame(null);
        Assert.assertFalse(test);
    }

    @Test
    public void getGameList() {
        SavedGames test = new SavedGames();
        test.addGame(g);
        Assert.assertEquals(test.getGameList().toString(), sg.getGameList().toString());
    }

//    @Test
//    public void getGamesInProgressTest() {
//        SavedGames test = new SavedGames();
//        test.addGame(g);
//        Assert.assertEquals(test.getGamesInProgress().toString(), sg.getGamesInProgress().toString());
//    }

    @Test
    public void getCompletedGamesTest() {
        Game completedGame = new Game(true, new Pente(), users);
        completedGame.makeMove(0,0,0); // Player 1 moves to 0, 0
        completedGame.makeMove(1,5,7);
        completedGame.makeMove(0,0,1);// Player 1 moves to 0, 1
        completedGame.makeMove(1,2,3);
        completedGame.makeMove(0,0,2); // Player 1 moves to 0, 2
        completedGame.makeMove(1,3,8);
        completedGame.makeMove(0,0,3); // Player 1 moves to 0, 3
        completedGame.makeMove(1,2,8);
        completedGame.makeMove(0,0,4);// Player 1 moves to 0, 4
        // Player 1 wins the game
        sg.addGame(completedGame);
        SavedGames test = new SavedGames();
        test.addGame(completedGame);
        Assert.assertEquals(test.getCompletedGames(u1.getUserName()), sg.getCompletedGames(u1.getUserName()));
    }

    @Test
    public void loadGame() {
        Game test = sg.loadGame(saveName);
        Assert.assertEquals(g, test);
    }

}