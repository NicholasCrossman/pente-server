package user;

//import main.java.User.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    User user;
    User user2;

    @Before
    public void setUp() throws Exception {
        user = new User("Temp", "bork");
        user2 = new User("Temp2", "bark");
    }

    @Test
    public void testGetUserName() {
        String name = "Temp";
        Assert.assertEquals(user.getUserName(), name);
    }

    @Test
    public void testAuthenticatePassword() {
        String password = "bork";
        Assert.assertTrue(user.authenticatePassword(password));
    }

    @Test
    public void testSetPassword() {
        String newPassword = "testing";
        Assert.assertTrue(user.setPassword(newPassword));
    }
    @Test
    public void getRecordTest(){
        Assert.assertEquals(user.getRecord().getWins(), 0);
    }
    @Test
    public void compareToTest(){
        Assert.assertEquals(user.compareTo(user2),0);
    }
}
