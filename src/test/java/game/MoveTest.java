package game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoveTest {
    Move m1;
    Move m2;
    @Before
    public void setUp() throws Exception {
        m1= new Move(1,1,0);
        m2= new Move(0,0,0);
    }

    @Test
    public void getPlayerTest() {
        Assert.assertEquals(1,m1.getPlayer());
    }

    @Test
    public void getLocationXTest() {
        Assert.assertEquals(1,m1.getLocationX());
    }

    @Test
    public void getLocationYTest() {
        Assert.assertEquals(0,m1.getLocationY());
    }

    @Test
    public void toStringWithoutDateTest() {
        String exampletxt = "1 1 0\n";
        Assert.assertEquals(m1.toStringWithoutDate(), exampletxt);
    }

}