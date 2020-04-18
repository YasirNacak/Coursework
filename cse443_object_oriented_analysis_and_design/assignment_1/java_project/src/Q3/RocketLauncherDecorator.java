package Q3;
/**
 * Suit decorator class that adds a laser to a suit
 * @author Yasir
 */
public class RocketLauncherDecorator extends SuitDecorator{
    /**
     * Constructor that takes a suit to decorate
     * @param baseSuit suit to add functionality to
     */
    public RocketLauncherDecorator(Suit baseSuit) {
        this._baseSuit = baseSuit;
    }

    @Override
    /**
     * Gets weight of this decorator
     * @return weight of this decorator
     */
    public float GetWeight() {
        return 7.5f + this._baseSuit.GetWeight();
    }

    @Override
    /**
     * Gets price of this decorator
     * @return price of this decorator
     */
    public int GetPrice() {
        return 150 + this._baseSuit.GetPrice();
    }
}
