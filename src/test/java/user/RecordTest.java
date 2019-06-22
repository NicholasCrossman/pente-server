package user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecordTest {
    Record r;
    @Before
    public void setUp() throws Exception {
        r = new Record();
    }

    @Test
    public void increaseWins() {
        r.increaseWins();
        Assert.assertEquals(r.getWins(),1);
    }

    @Test
    public void increaseLosses() {
        r.increaseLosses();
        Assert.assertEquals(r.getLosses(),1);
    }

    @Test
    public void increaseTies() {
        r.increaseTies();
        Assert.assertEquals(r.getTies(),1);
    }
}