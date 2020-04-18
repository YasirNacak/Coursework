package Q3;

/**
 * Tor type of suits
 * @author Yasir
 */
public class TorSuit implements Suit {
    @Override
    /**
     * Gets weight of the suit
     * @return weight of the suit
     */
    public float GetWeight() {
        return 50.0f;
    }

    @Override
    /**
     * Gets price of the suit
     * @return price of the suit
     */
    public int GetPrice() {
        return 5000;
    }
}
