package Q3;

/**
 * Dec type of suits
 * @author Yasir
 */
public class DecSuit implements Suit {
    @Override
    /**
     * Gets weight of the suit
     * @return weight of the suit
     */
    public float GetWeight() {
        return 25.0f;
    }

    /**
     * Gets price of the suit
     * @return price of the suit
     */
    @Override
    public int GetPrice() {
        return 500;
    }
}
