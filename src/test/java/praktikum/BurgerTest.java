package praktikum;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.dataObjects.BurgerData;

import java.util.ArrayList;
import java.util.List;
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
    private static float ingredientPrice;
    private static float bunPrice;
    private static IngredientType ingredientType;
    private static final int INGREDIENTS_COUNT = 10;

    @BeforeClass
    public static void setUpData() {
        bunName = BurgerData.getBunName();
        bunPrice = BurgerData.getBunPrice();
        ingredientName = BurgerData.getIngredientName();
        ingredientPrice = BurgerData.getIngredientPrice();
        ingredientType = BurgerData.getIngredientType();
    }

    @Before
    public void setUp() {
        actualBurger = new Burger();
        Mockito.when(mockedBun.getPrice()).thenReturn(bunPrice);
        Mockito.when(mockedIngredient.getPrice()).thenReturn(ingredientPrice);
        Mockito.when(mockedBun.getName()).thenReturn(bunName);
        Mockito.when(mockedIngredient.getName()).thenReturn(ingredientName);
        Mockito.when(mockedIngredient.getType()).thenReturn(ingredientType);
    }

    @Test
    public void setBunsSetsPassedBunToFieldInBurger() {
        actualBurger.setBuns(mockedBun);
        Bun actualBunInBurger = actualBurger.bun;
        assertEquals(mockedBun, actualBunInBurger);
    }

    @Test
    public void addIngredientShouldAddIngredientToListOfIngredients() {
        List<Ingredient> expectedIngredientsInBurger = new ArrayList<>();
        List<Ingredient> actualIngredientsInBurger = actualBurger.ingredients;

        for(int i = 0; i < INGREDIENTS_COUNT; i++) {
            expectedIngredientsInBurger.add(mockedIngredient);
            actualBurger.addIngredient(mockedIngredient);
        }

        assertEquals(expectedIngredientsInBurger, actualIngredientsInBurger);

    }

    @Test
    public void removeIngredientRemoveIngredientFormListWithPassedIndex() {
        List<Ingredient> expectedIngredientsInBurger = new ArrayList<>();
        List<Ingredient> actualIngredientsInBurger = actualBurger.ingredients;

        for(int i = 0; i < INGREDIENTS_COUNT; i++) {
            expectedIngredientsInBurger.add(mockedIngredient);
            actualBurger.addIngredient(mockedIngredient);
        }

        int indexOfRemovedIngredient = new Random().nextInt(INGREDIENTS_COUNT);
        expectedIngredientsInBurger.remove(indexOfRemovedIngredient);
        actualBurger.removeIngredient(indexOfRemovedIngredient);

        assertEquals(expectedIngredientsInBurger, actualIngredientsInBurger);
    }

    // В данном тесте не использовал замоканный ингридиент,
    // т.к. все элементы массива ссылваются на 1 объект
    // из чего следует что, как не меняй местами ссылки на 1 объект всегда будет ложнопозитивный результат
    @Test
    public void moveIngredientShouldMoveIngredientToPassedIndex() {

        List<Ingredient> expectedIngredientsInBurger = new ArrayList<>();
        List<Ingredient> actualIngredientsInBurger = actualBurger.ingredients;
        Ingredient ingredientToBurger;

        for(int i = 0; i < INGREDIENTS_COUNT; i++) {
            ingredientToBurger = new Ingredient(ingredientType, ingredientName, ingredientPrice);
            expectedIngredientsInBurger.add(ingredientToBurger);
            actualBurger.addIngredient(ingredientToBurger);
        }

        int oldIndex = new Random().nextInt(INGREDIENTS_COUNT);
        int newIndex = new Random().nextInt(INGREDIENTS_COUNT);
        while (oldIndex == newIndex) {
            oldIndex = new Random().nextInt(INGREDIENTS_COUNT);
        }

        expectedIngredientsInBurger.add(newIndex, expectedIngredientsInBurger.remove(oldIndex));
        actualBurger.moveIngredient(oldIndex, newIndex);

        assertEquals(expectedIngredientsInBurger, actualIngredientsInBurger);
    }

    @Test
    public void getPriceShouldReturnSumPriceOfAllBurgersComponents() {
        actualBurger.setBuns(mockedBun);
        addIngredientsToTestedBurger(INGREDIENTS_COUNT);

        float actualPrice = actualBurger.getPrice();
        Mockito.verify(mockedBun).getPrice();
        Mockito.verify(mockedIngredient, times(INGREDIENTS_COUNT)).getPrice();
        float expectedPrice = calculateExpectedBurgerPrice(INGREDIENTS_COUNT);

        assertEquals(expectedPrice, actualPrice, 0.0001);
    }

    @Test
    public void getReceiptShouldReturnStringThatDisplaysBurger() {

        addIngredientsToTestedBurger(INGREDIENTS_COUNT);

        StringBuilder expectedReceiptBuilder = new StringBuilder(String.format("(==== %s ====)%n", bunName));
        for (int i = 0; i < INGREDIENTS_COUNT; i++) {
            expectedReceiptBuilder.append(String.format("= %s %s =%n", ingredientType.toString().toLowerCase(),
                    ingredientName));
        }
        expectedReceiptBuilder.append(String.format("(==== %s ====)%n", bunName));
        expectedReceiptBuilder.append(String.format("%nPrice: %f%n", calculateExpectedBurgerPrice(INGREDIENTS_COUNT)));
        String expectedReceipt = expectedReceiptBuilder.toString();

        actualBurger.setBuns(mockedBun);
        String actualReceipt = actualBurger.getReceipt();

        Mockito.verify(mockedBun, Mockito.times(2)).getName();
        Mockito.verify(mockedIngredient, times(INGREDIENTS_COUNT)).getName();
        Mockito.verify(mockedIngredient, times(INGREDIENTS_COUNT)).getType();
        assertEquals(expectedReceipt, actualReceipt);

    }

    private void addIngredientsToTestedBurger(int ingredientsCount) {
        Stream.generate(() -> mockedIngredient).limit(ingredientsCount)
                .forEach((ingredient)->actualBurger.addIngredient(ingredient));
    }

    private float calculateExpectedBurgerPrice(int ingredientCount) {
        return bunPrice * 2 + ingredientPrice * ingredientCount;
    }

}
