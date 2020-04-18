package Q1;

import Utilities.Color;
import Utilities.Image;

import java.util.Random;

public class SaltAndPepperNoise {
    private static int _width;
    private static int _height;

    private static Image _result;

    private static void SetupResult(Image source) {
        _width = source.GetWidth();
        _height = source.GetHeight();
        _result = new Image(_width, _height);
    }

    public static Image Apply(Image source, int noisePercentage) {
        SetupResult(source);

        Random rng = new Random();

        for (int i = 0; i < source.GetWidth(); i++) {
            for (int j = 0; j < source.GetHeight(); j++) {
                _result.PixelColors[i][j] = source.PixelColors[i][j];
            }
        }
        
        for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
                int noiseChance = rng.nextInt(100) + 1;

                if(noiseChance < noisePercentage) {
                    int noiseColor = rng.nextInt(2);
                    _result.PixelColors[i][j] = noiseColor == 0 ? new Color(0, 0, 0) : new Color(255, 255, 255);
                }
            }
        }

        _result.ApplyPixelColors();

        return _result;
    }
}
