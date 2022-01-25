package praktikum;

import org.junit.Before;
import org.junit.Test;
import praktikum.dataObjects.BunData;

import static org.junit.Assert.assertEquals;

public class BunTest {

    private Bun testedBun;
    private String expectedNameBun;
    private float expectedPriceBun;

    @Before
    public void BunInit() {
        expectedNameBun = BunData.getBunName();
        expectedPriceBun = BunData.getBunPrice();
        testedBun = new Bun(expectedNameBun, expectedPriceBun);
    }

    @Test
    public void getNameReturnsName() {
        String actualNameBun = testedBun.getName();
        assertEquals(expectedNameBun, actualNameBun);
    }

    @Test
    public void getPriceReturnsPrice() {
        float actualPriceBun = testedBun.getPrice();
        assertEquals(expectedPriceBun, actualPriceBun, 0.001);
    }
}