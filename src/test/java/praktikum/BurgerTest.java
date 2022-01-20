package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    @Mock
    private Bun mockedBun;

    @Mock
    private Ingredient mockedIngredient;

    public Burger actualBurger;

    @Before
    public void setUp() {
        actualBurger = new Burger();
        Mockito.when(mockedBun.getPrice()).thenReturn(1F);
        Mockito.when(mockedIngredient.getPrice()).thenReturn(1F);
    }

    @Test
    public void setBuns() {
    }

    @Test
    public void addIngredient() {
    }

    @Test
    public void removeIngredient() {
    }

    @Test
    public void moveIngredient() {
    }

    @Test
    public void getPrice() {
        actualBurger.setBuns(mockedBun);
        int randomIngredientsCount = new Random().nextInt(10);
        Stream.generate(() -> mockedIngredient).limit(randomIngredientsCount)
                .forEach((ingredient)->actualBurger.addIngredient(ingredient));

        float actualPrice = actualBurger.getPrice();
        Mockito.verify(mockedBun).getPrice();
        Mockito.verify(mockedIngredient, Mockito.times(randomIngredientsCount)).getPrice();
        float expectedPrice = mockedBun.getPrice() * 2 + mockedIngredient.getPrice() * randomIngredientsCount;

        assertEquals(expectedPrice, actualPrice, 0.0001);
    }

    @Test
    public void getReceipt() {


    }
}
