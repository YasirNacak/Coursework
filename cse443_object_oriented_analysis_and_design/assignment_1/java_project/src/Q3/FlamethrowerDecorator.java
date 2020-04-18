package Q3;

/**
 * Suit decorator class that adds a flamethrower to a suit
 * @author Yasir
 */
public class FlamethrowerDecorator extends SuitDecorator {
    /**
     * Constructor that takes a suit to decorate
     * @param baseSuit suit to add functionality to
     */
    public FlamethrowerDecorator(Suit baseSuit) {
        this._baseSuit = baseSuit;
    }

    @Override
    /**
     * Gets weight of this decorator
     * @return weight of this decorator
     */
    public float GetWeight() {
        return 2.0f + this._baseSuit.GetWeight();
    }

    @Override
    /**
     * Gets price of this decorator
     * @return price of this decorator
     */
    public int GetPrice() {
        return 50 + this._baseSuit.GetPrice();
    }
}
