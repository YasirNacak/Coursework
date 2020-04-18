package Q1;

import Utilities.Color;

import java.util.Comparator;

public abstract class ColorComparator implements Comparator<Color> {
    @Override
    public abstract int compare(Color o1, Color o2);
}
