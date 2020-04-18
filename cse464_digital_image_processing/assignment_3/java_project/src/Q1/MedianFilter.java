package Q1;

import Utilities.Color;
import Utilities.Image;

import java.util.ArrayList;
import java.util.Collections;

public class MedianFilter {
    private static int _width;
    private static int _height;

    private static Image _result;

    private static void SetupResult(Image source) {
        _width = source.GetWidth();
        _height = source.GetHeight();
        _result = new Image(_width, _height);
    }

    public static Image Apply(Image source, int filterSize, ColorComparator compareMethod) {
        SetupResult(source);

        for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
                ArrayList<Color> neighbors = new ArrayList<>();

                int startOffset = filterSize / 2;

                for (int k = i - startOffset; k < i - startOffset + filterSize; k++) {
                    for (int l = j - startOffset; l < j - startOffset + filterSize; l++) {
                        neighbors.add(source.Get(k, l));
                    }
                }

                neighbors.sort(compareMethod);

                _result.PixelColors[i][j] = neighbors.get(neighbors.size() / 2);
            }
        }

        _result.ApplyPixelColors();

        return _result;
    }

    public static Image Apply(Image source, int filterSize) {
        SetupResult(source);

        for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
                ArrayList<Integer> reds = new ArrayList<>();
                ArrayList<Integer> greens = new ArrayList<>();
                ArrayList<Integer> blues = new ArrayList<>();

                int startOffset = filterSize / 2;

                for (int k = i - startOffset; k < i - startOffset + filterSize; k++) {
                    for (int l = j - startOffset; l < j - startOffset + filterSize; l++) {
                        reds.add(source.Get(k, l).GetRed());
                        greens.add(source.Get(k, l).GetGreen());
                        blues.add(source.Get(k, l).GetBlue());
                    }
                }

                Collections.sort(reds);
                Collections.sort(greens);
                Collections.sort(blues);

                _result.PixelColors[i][j] = new Color(reds.get(reds.size() / 2), greens.get(greens.size() / 2), blues.get(blues.size() / 2));
            }
        }

        _result.ApplyPixelColors();

        return _result;
    }
}
