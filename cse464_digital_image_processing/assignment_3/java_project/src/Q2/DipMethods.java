package Q2;

import Utilities.Color;
import Utilities.Image;

public class DipMethods {
    public static Image Equalize(Image source) {
        int width = source.GetWidth();
        int height = source.GetHeight();
        Image result = new Image(width, height);

        int anzPixel= width * height;

        int[] histogram = new int[256];
        int[] iArray = new int[1];

        int i = 0;

        for(int x = 1; x < width; x++) {
            for(int y = 1; y < height; y++) {
                int valueBefore = source.Get(x, y).GetRed();
                histogram[valueBefore]++;
            }
        }

        int sum = 0;
        float[] lut = new float[anzPixel];
        for(i = 0; i < 255; i++) {
            sum += histogram[i];
            lut[i] = sum * 255 / anzPixel;
        }

        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                int valueBefore = source.Get(x, y).GetRed();
                int valueAfter = (int) lut[valueBefore];
                iArray[0] = valueAfter;
                result.PixelColors[x][y] = new Color(valueAfter, valueAfter, valueAfter);
            }
        }

        result.ApplyPixelColors();

        return result;
    }

    public static int[] CalculateLBPHistogram(Image source) {
        source = DipMethods.Equalize(source);

        int[] histogram = new int[256];

        for (int i = 0; i < source.GetWidth(); i++) {
            for (int j = 0; j < source.GetHeight(); j++) {
                int outValue = 0;

                for (int k = i - 1; k < i + 2; k++) {
                    for (int l = j - 1; l < j + 2; l++) {
                        if(!(k == i && l == j)) {
                            if(source.Get(i, j).GetRed() >= source.Get(k, l).GetRed()) {
                                outValue = (outValue << 1);
                            } else {
                                outValue = (outValue << 1) | 1;
                            }
                        }
                    }
                }

                histogram[outValue]++;
            }
        }

        return histogram;
    }
}
