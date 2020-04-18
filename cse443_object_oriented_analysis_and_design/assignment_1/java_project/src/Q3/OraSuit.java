package Q3;

/**
 * Ora type of suits
 * @author Yasir
 */
public class OraSuit implements Suit {
    @Override
    /**
     * Gets weight of the suit
     * @return weight of the suit
     */
    public float GetWeight() {
        return 30.0f;
    }

    @Override
    /**
     * Gets price of the suit
     * @return price of the suit
     */
    public int GetPrice() {
        return 1500;
    }
}
