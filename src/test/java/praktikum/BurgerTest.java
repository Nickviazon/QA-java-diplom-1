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
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    @Mock
    private Bun mockedBun;

    @Mock
    private Ingredient mockedIngredient;

    public Burger actualBurger;

    private static final float PRICE_FOR_BUN_AND_INGREDIENT = 1F;
    private static final String BUN_NAME = "bun";
    private static final String INGREDIENT_NAME  = "ingredient";
    private static final IngredientType INGREDIENT_TYPE = IngredientType.values()[0];

    @Before
    public void setUp() {
        actualBurger = new Burger();
        Mockito.when(mockedBun.getPrice()).thenReturn(PRICE_FOR_BUN_AND_INGREDIENT);
        Mockito.when(mockedIngredient.getPrice()).thenReturn(PRICE_FOR_BUN_AND_INGREDIENT);
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
        addIngredientsToTestedBurger(randomIngredientsCount);

        float actualPrice = actualBurger.getPrice();
        Mockito.verify(mockedBun).getPrice();
        Mockito.verify(mockedIngredient, times(randomIngredientsCount)).getPrice();
        float expectedPrice = calculateExpectedBurgerPrice(randomIngredientsCount);

        assertEquals(expectedPrice, actualPrice, 0.0001);
    }

    @Test
    public void getReceipt() {
        Mockito.when(mockedBun.getName()).thenReturn(BUN_NAME);
        Mockito.when(mockedIngredient.getName()).thenReturn(INGREDIENT_NAME);
        Mockito.when(mockedIngredient.getType()).thenReturn(INGREDIENT_TYPE);

        int randomIngredientsCount = new Random().nextInt(10);
        addIngredientsToTestedBurger(randomIngredientsCount);

        StringBuilder expectedReceiptBuilder = new StringBuilder(String.format("(==== %s ====)%n", BUN_NAME));
        for (int i = 0; i < randomIngredientsCount; i++) {
            expectedReceiptBuilder.append(String.format("= %s %s =%n", INGREDIENT_TYPE.toString().toLowerCase(),
                    INGREDIENT_NAME));
        }
        expectedReceiptBuilder.append(String.format("(==== %s ====)%n", BUN_NAME));
        expectedReceiptBuilder.append(String.format("%nPrice: %f%n", calculateExpectedBurgerPrice(randomIngredientsCount)));
        String expectedReceipt = expectedReceiptBuilder.toString();

        actualBurger.setBuns(mockedBun);
        String actualReceipt = actualBurger.getReceipt();

        Mockito.verify(mockedBun, Mockito.times(2)).getName();
        Mockito.verify(mockedIngredient, times(randomIngredientsCount)).getName();
        Mockito.verify(mockedIngredient, times(randomIngredientsCount)).getType();
        assertEquals(expectedReceipt, actualReceipt);

    }

    private void addIngredientsToTestedBurger(int ingredientsCount) {
        Stream.generate(() -> mockedIngredient).limit(ingredientsCount)
                .forEach((ingredient)->actualBurger.addIngredient(ingredient));
    }

    private float calculateExpectedBurgerPrice(int ingredientCount) {
        return PRICE_FOR_BUN_AND_INGREDIENT * 2 + PRICE_FOR_BUN_AND_INGREDIENT * ingredientCount;
    }

}
