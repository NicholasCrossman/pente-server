package game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
    Board b1;
    @Before
    public void setUp() throws Exception {
        b1= new Board(19);
    }

    @Test
    public void getBoardCellsTest() {
        int[][] exampleArray = new int[19][19];
        for (int i = 0; i < 19; i++){
            for (int j = 0; j < 19; j++){
                exampleArray[i][j] = -1;
            }
        }
        Assert.assertArrayEquals(exampleArray,b1.getBoardCells());
    }
    @Test
    public void setBoardCellsTest() {
        int[][] exampleArray = new int[19][19];
        for (int i = 0; i < 19; i++){
            for (int j = 0; j < 19; j++){
                exampleArray[i][j] = 1;
            }
        }
        b1.setBoardCells(exampleArray);
        Assert.assertArrayEquals(exampleArray,b1.getBoardCells());
    }
    @Test
    public void setCellWithCellStateTest(){
        b1.setCell(1, 0,0);
        Assert.assertEquals(1,b1.cellState(0,0));
    }




}