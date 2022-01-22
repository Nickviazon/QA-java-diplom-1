package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParameterizedIngredientTest {

    private final static float ingredientPrice = 1F;
    private final static String ingredientName = "ingredient";
    private final static IngredientType[] arrayOfIngredientTypes = IngredientType.values();

    private Ingredient actualIngredient;

    @Parameterized.Parameter()
    public static IngredientType actualIngredientType;

    @Parameterized.Parameter(1)
    public static  IngredientType expectedIngredientType;

    @Parameterized.Parameters(name = "ingredient.getType({0}) -> {1}")
    public static  Object[] inputAndExpectedIngredientTypes() {
        // Сделал так чтобы вне зависитости от заполненности перечисления IngredientType проходили проверки
        return Arrays.stream(arrayOfIngredientTypes)
                .map(ingredientType -> new IngredientType[] {ingredientType, ingredientType})
                .toArray();
    }

    @Before
    public void setUpIngredient() {
        actualIngredient = new Ingredient(actualIngredientType, ingredientName, ingredientPrice);
    }

    @Test
    public void getTypeShouldReturnIngredientTypePassedInIngredientConstructor() {
        IngredientType actualIngredientType = actualIngredient.getType();

        assertEquals(expectedIngredientType, actualIngredientType);
    }
}