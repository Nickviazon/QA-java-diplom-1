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
        int ingredientsCount = new Random().nextInt(10);
        List<Ingredient> expectedIngredientsInBurger = new ArrayList<>();
        List<Ingredient> actualIngredientsInBurger = actualBurger.ingredients;

        for(int i = 0; i < ingredientsCount; i++) {
            expectedIngredientsInBurger.add(mockedIngredient);
            actualBurger.addIngredient(mockedIngredient);
        }

        assertEquals(expectedIngredientsInBurger, actualIngredientsInBurger);

    }

    @Test
    public void removeIngredientRemoveIngredientFormListWithPassedIndex() {
        int ingredientsCount = new Random().nextInt(10);
        List<Ingredient> expectedIngredientsInBurger = new ArrayList<>();
        List<Ingredient> actualIngredientsInBurger = actualBurger.ingredients;

        for(int i = 0; i < ingredientsCount; i++) {
            expectedIngredientsInBurger.add(mockedIngredient);
            actualBurger.addIngredient(mockedIngredient);
        }

        int indexOfRemovedIngredient = new Random().nextInt(ingredientsCount);
        expectedIngredientsInBurger.remove(indexOfRemovedIngredient);
        actualBurger.removeIngredient(indexOfRemovedIngredient);

        assertEquals(expectedIngredientsInBurger, actualIngredientsInBurger);
    }

    // В данном тесте не использовал замоканный ингридиент,
    // т.к. все элементы массива ссылваются на 1 объект
    // из чего следует что, как не меняй местами ссылки на 1 объект всегда будет ложнопозитивный результат
    @Test
    public void moveIngredientShouldMoveIngredientToPassedIndex() {

        int ingredientsCount = new Random().nextInt(10);
        while (ingredientsCount == 1) {
            ingredientsCount = new Random().nextInt(10);
        }
        List<Ingredient> expectedIngredientsInBurger = new ArrayList<>();
        List<Ingredient> actualIngredientsInBurger = actualBurger.ingredients;
        Ingredient ingredientToBurger;

        for(int i = 0; i < ingredientsCount; i++) {
            ingredientToBurger = new Ingredient(ingredientType, ingredientName, ingredientPrice);
            expectedIngredientsInBurger.add(ingredientToBurger);
            actualBurger.addIngredient(ingredientToBurger);
        }

        int oldIndex = new Random().nextInt(ingredientsCount);
        int newIndex = new Random().nextInt(ingredientsCount);
        while (oldIndex == newIndex) {
            oldIndex = new Random().nextInt(ingredientsCount);
        }

        expectedIngredientsInBurger.add(newIndex, expectedIngredientsInBurger.remove(oldIndex));
        actualBurger.moveIngredient(oldIndex, newIndex);

        assertEquals(expectedIngredientsInBurger, actualIngredientsInBurger);
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
        return bunPrice * 2 + ingredientPrice * ingredientCount;
    }

}
