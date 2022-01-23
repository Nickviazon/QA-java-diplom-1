package praktikum;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.POJOforTests.BurgerData;

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

    private static Burger actualBurger;
    private static String bunName;
    private static String ingredientName;
    private static float priceForBunAndIngredient;
    private static IngredientType ingredientType;

    @BeforeClass
    public static void setUpData() {
        BurgerData burgerData = new BurgerData();
        bunName = burgerData.getBunName();
        ingredientName = burgerData.getIngredientName();
        priceForBunAndIngredient = burgerData.getPriceForBunAndIngredient();
        ingredientType = burgerData.getIngredientType();
    }

    @Before
    public void setUp() {
        actualBurger = new Burger();
        Mockito.when(mockedBun.getPrice()).thenReturn(priceForBunAndIngredient);
        Mockito.when(mockedIngredient.getPrice()).thenReturn(priceForBunAndIngredient);
    }

    @Test
    public void setBunsSetsPassedBunToFieldInBurger() {
        actualBurger.setBuns(mockedBun);
        Bun actualBunInBurger = actualBurger.bun;
        assertEquals(mockedBun, actualBunInBurger);
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
    public void getPriceShouldReturnSumPriceOfAllBurgersComponents() {
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
    public void getReceiptShouldReturnStringThatDisplaysBurger() {
        Mockito.when(mockedBun.getName()).thenReturn(bunName);
        Mockito.when(mockedIngredient.getName()).thenReturn(ingredientName);
        Mockito.when(mockedIngredient.getType()).thenReturn(ingredientType);

        int randomIngredientsCount = new Random().nextInt(10);
        addIngredientsToTestedBurger(randomIngredientsCount);

        StringBuilder expectedReceiptBuilder = new StringBuilder(String.format("(==== %s ====)%n", bunName));
        for (int i = 0; i < randomIngredientsCount; i++) {
            expectedReceiptBuilder.append(String.format("= %s %s =%n", ingredientType.toString().toLowerCase(),
                    ingredientName));
        }
        expectedReceiptBuilder.append(String.format("(==== %s ====)%n", bunName));
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
        return priceForBunAndIngredient * 2 + priceForBunAndIngredient * ingredientCount;
    }

}
