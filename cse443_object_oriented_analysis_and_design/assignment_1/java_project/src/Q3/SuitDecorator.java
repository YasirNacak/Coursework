package Q3;

/**
 * Decorator class that extends the functionality of suits
 * @author Yasir
 */
public abstract class SuitDecorator implements Suit {
    /**
     * Suit that this decorator adds functionality to
     */
    protected Suit _baseSuit;

    /**
     * Gets weight of this decorator
     * @return weight of this decorator
     */
    public abstract float GetWeight();

    /**
     * Gets price of this decorator
     * @return price of this decorator
     */
    public abstract int GetPrice();
}
