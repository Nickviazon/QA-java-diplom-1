package praktikum.dataObjects;

import praktikum.IngredientType;

public class IngredientData {
    private static final String INGREDIENT_NAME = "ingredient";
    private static final float INGREDIENT_PRICE = 1f;
    private static final IngredientType INGREDIENT_TYPE = IngredientType.values()[0];

    public static String getIngredientName() {
        return INGREDIENT_NAME;
    }

    public static float getIngredientPrice() {
        return INGREDIENT_PRICE;
    }

    public static IngredientType getIngredientType() {
        return  INGREDIENT_TYPE;
    }
}
