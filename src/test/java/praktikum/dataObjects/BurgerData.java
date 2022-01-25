package praktikum.dataObjects;

import praktikum.IngredientType;

public class BurgerData {
    private static final String BUN_NAME = "bun";
    private static final float BUN_PRICE = IngredientData.getIngredientPrice();
    private static final String INGREDIENT_NAME  = "ingredient";
    private static final float INGREDIENT_PRICE = IngredientData.getIngredientPrice();
    private static final IngredientType INGREDIENT_TYPE = IngredientType.values()[0];

    public static float getIngredientPrice() {
        return INGREDIENT_PRICE;
    }

    public static float getBunPrice() {
        return  BUN_PRICE;
    }

    public static String getBunName() {
        return BUN_NAME;
    }

    public static String getIngredientName() {
        return INGREDIENT_NAME;
    }

    public static IngredientType getIngredientType() {
        return INGREDIENT_TYPE;
    }
}
