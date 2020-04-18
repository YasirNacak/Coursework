package Q1;

import Utilities.Color;

public class NormBasedComparator extends ColorComparator {
    @Override
    public int compare(Color o1, Color o2) {
        double NColor1 = Math.sqrt(Math.pow(o1.GetRed(), 2) + Math.pow(o1.GetGreen(), 2) + Math.pow(o1.GetBlue(), 2));
        double NColor2 = Math.sqrt(Math.pow(o2.GetRed(), 2) + Math.pow(o2.GetGreen(), 2) + Math.pow(o2.GetBlue(), 2));
        return Double.compare(NColor1, NColor2);
    }
}
