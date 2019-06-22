package server;

import user.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

public class UserListTest {

    @Test
    public void addUser() {
        UserList uList = new UserList();
        String firstUserName = "FirstUser";
        User first = new User(firstUserName, "password");
        User second = new User(firstUserName, "password2");
        uList.addUser(first);
        Assert.assertFalse(uList.addUser(second));
    }

    @Test
    public void getUserByName() {
        UserList uList = new UserList();
        String firstUserName = "FirstUser";
        User first = new User(firstUserName, "password");
        uList.addUser(first);
        Assert.assertEquals(firstUserName, uList.getUserByName(firstUserName).getUserName());
    }

//    @Test
//    public void saveLoadUserListTest() {
//        UserList uList = new UserList();
//        String firstUserName = "FirstUser";
//        User first = new User(firstUserName, "password");
//        uList.addUser(first);
//        // save uList and load it into another UserList
//        uList.saveUserList();
//        UserList test = new UserList(true);
//        Assert.assertEquals(uList.toString(), test.toString());
//    }
    @Test
    public void generateLeaderBoardTest(){
        UserList uList = new UserList();
        UserList uList2 = new UserList();
        User first = new User("A", "password");
        User second = new User("B", "password");
        User third = new User("C", "password");
        User fourth = new User("D", "password");
        User fifth = new User("E", "password");
        fifth.getRecord().increaseWins();
        fifth.getRecord().increaseWins();
        third.getRecord().increaseWins();
        uList.addUser(first);
        uList.addUser(second);
        uList.addUser(third);
        uList.addUser(fourth);
        uList.addUser(fifth);

        uList2.addUser(first);
        uList2.addUser(second);
        uList2.addUser(third);
        uList2.addUser(fourth);
        uList2.addUser(fifth);


        String leaderBoard = uList.generateLeaderBoard();
        String leaderBoard2 = uList2.generateLeaderBoard();
        System.out.print(leaderBoard);
        Assert.assertTrue(leaderBoard.equals(leaderBoard2));

    }
}