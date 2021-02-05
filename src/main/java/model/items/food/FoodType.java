package model.items.food;

/**
 * Represents an enumeration for declaring foods types.
 * 
 * The field keeps track of the food's starvation value.
 *
 */
public enum FoodType {

    /** 
     * Apple.
     */
    APPLE(5),
    /**
     * Cake.
     */
    CAKE(10),
    /**
     * Soup.
     */
    SOUP(8),
    /**
     * Hamburger.
     */
    HAMBURGER(12),
    /**
     * Cheese.
     */
    CHEESE(4),
    /**
     * Steak.
     */
    STEAK(8),
    /**
     * Bread.
     */
    BREAD(6);

    /*
     * The higher the starvationValue of a food is, the longer the player's
     * hunger will last.
     */
    private final int starvationValue;

    /**
     * @return the food's starvation value.
     */
    public int getStarvationValue() {
        return starvationValue;
    }

    FoodType(final int value) {
        starvationValue = value;
    }

}
