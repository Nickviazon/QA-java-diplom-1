package praktikum;

import org.junit.Before;
import org.junit.Test;
import praktikum.POJOforTests.IngredientData;

import static org.junit.Assert.assertEquals;

public class NotParameterizedIngredientTest {

    private String expectedIngredientName;
    private float expectedIngredientPrice;
    private Ingredient actualIngredient;

    @Before
    public void setUpIngredient() {
        IngredientData expectedIngredient = new IngredientData();
        expectedIngredientName = expectedIngredient.getIngredientName();
        expectedIngredientPrice = expectedIngredient.getIngredientPrice();
        IngredientType ingredientType = expectedIngredient.getIngredientType();
        actualIngredient = new Ingredient(ingredientType, expectedIngredientName, expectedIngredientPrice);
    }

    @Test
    public void getNameShouldReturnNamePassedInIngredientConstructor() {
        String actualIngredientName = actualIngredient.getName();
        assertEquals(expectedIngredientName, actualIngredientName);
    }

    @Test
    public void getPriceShouldReturnPricePassedInIngredientConstructor() {
        float actualIngredientPrice = actualIngredient.getPrice();
        assertEquals(expectedIngredientPrice, actualIngredientPrice, 0.0001F);
    }


}
