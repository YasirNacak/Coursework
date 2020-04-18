package Utilities;

public class Color {
    private int _red;
    private int _green;
    private int _blue;

    public Color() {
        _red = 0;
        _green = 0;
        _blue = 0;
    }

    public Color(int red, int green, int blue) {
        this._red = red;
        this._green = green;
        this._blue = blue;
    }

    public int GetRed() {
        return _red;
    }

    public int GetGreen() {
        return _green;
    }

    public int GetBlue() {
        return _blue;
    }

    public void SetRed(int red) {
        _red = red;
    }

    public void SetGreen(int green) {
        _green = green;
    }

    public void SetBlue(int blue) {
        _blue = blue;
    }

    public int GetRGBInt() {
        int result = _red;
        result = (result << 8) + _green;
        result = (result << 8) + _blue;
        return result;
    }

    @Override
    public String toString() {
        return "Col[" +
                "R="   + _red +
                ", G=" + _green +
                ", B=" + _blue +
                ']';
    }
}
