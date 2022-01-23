package praktikum.POJOforTests;

import praktikum.IngredientType;

public class BurgerData {
    private static final float PRICE_FOR_BUN_AND_INGREDIENT = 1F;
    private static final String BUN_NAME = "bun";
    private static final String INGREDIENT_NAME  = "ingredient";
    private static final IngredientType INGREDIENT_TYPE = IngredientType.values()[0];

    public float getPriceForBunAndIngredient() {
        return PRICE_FOR_BUN_AND_INGREDIENT;
    }

    public String getBunName() {
        return BUN_NAME;
    }

    public String getIngredientName() {
        return INGREDIENT_NAME;
    }

    public IngredientType getIngredientType() {
        return INGREDIENT_TYPE;
    }
}
