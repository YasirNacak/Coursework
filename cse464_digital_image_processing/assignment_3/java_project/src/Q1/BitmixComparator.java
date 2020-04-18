package Q1;

import Utilities.Color;

public class BitmixComparator extends ColorComparator {
    @Override
    public int compare(Color o1, Color o2) {
        int o1MixedBits = MixBits(o1);
        int o2MixedBits = MixBits(o2);
        return Integer.compare(o1MixedBits, o2MixedBits);
    }

    private int GetBit(int num , int shiftAmount){
        return (num >> shiftAmount) & 1;
    }

    private int MixBits(Color c){
        int result = 0;

        for(int i = 7 ; i >= 0 ; i--){
            result |= GetBit(c.GetRed(), i);
            result = result << 1;
            result |= GetBit(c.GetGreen(), i);
            result = result << 1;
            result |= GetBit(c.GetBlue(), i);
            result = result << 1;
        }

        result = result >> 1;

        return result;
    }
}
