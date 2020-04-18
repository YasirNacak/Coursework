package Q1;

import Utilities.Color;

public class LexicographicalComparator extends ColorComparator {
    @Override
    public int compare(Color c1, Color c2) {
        if(c1.GetRed() < c2.GetRed()) {
            return -1;
        } else if(c1.GetRed() > c2.GetRed()) {
            return 1;
        }

        if(c1.GetGreen() < c2.GetGreen()) {
            return -1;
        }
        else if(c1.GetGreen() > c2.GetGreen()) {
            return 1;
        }

        if(c1.GetBlue() < c2.GetBlue()) {
            return -1;
        }
        else if(c1.GetBlue() > c2.GetBlue()) {
            return 1;
        }

        return 0;

    }
}