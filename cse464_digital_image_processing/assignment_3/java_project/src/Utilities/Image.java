package Utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Image {
    private BufferedImage _image;

    private int _width;

    private int _height;

    public  Color[][] PixelColors;

    private String _imageFilename;

    public Image(int width, int height) {
        this._width = width;
        this._height = height;
        PixelColors = new Color[_width][_height];
        _image = new BufferedImage(_width, _height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                PixelColors[j][i] = new Color(255, 255, 255);
            }
        }
    }

    public Image(String path, boolean isGrayscale) {
        this.Load(path, isGrayscale);
    }

    private void Load(String path, boolean isGrayscale) {
        try {
            Path fPath = Paths.get(path);
            Path filename = fPath.getFileName();
            _imageFilename = filename.toString();
            _image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println(path + " is not a valid image file!");
        }

        _width = _image.getWidth();
        _height = _image.getHeight();
        PixelColors = new Color[_width][_height];

        if(!isGrayscale) {
            byte[] pixels = ((DataBufferByte) _image.getRaster().getDataBuffer()).getData();
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += 3) {
                int r = (int) pixels[pixel + 2] & 0xff;
                int g = (int) pixels[pixel + 1] & 0xff;
                int b = (int) pixels[pixel + 0] & 0xff;

                Color c = new Color(r, g, b);
                PixelColors[col][row] = c;

                col++;
                if (col == _width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            Raster raster = _image.getData();
            for (int i = 0; i < _width; i++) {
                for (int j = 0; j < _height; j++) {
                    int val = raster.getSample(i, j, 0);
                    PixelColors[i][j] = new Color(val, val, val);
                }
            }
        }
    }

    public int GetWidth() {
        return _width;
    }

    public int GetHeight() {
        return _height;
    }

    public Color Get(int x, int y) {
        if(x < 0) {
            x = -x;
        }

        if(x == _width) {
            x--;
        }

        if(x > _width) {
            x -= (x - _width) * 2;
        }

        if(y < 0) {
            y = -y;
        }

        if(y == _height) {
            y--;
        }

        if(y > _height) {
            y -= (y - _height) * 2;
        }

        return PixelColors[x][y];
    }

    public String GetImageFileName() {
        return _imageFilename;
    }

    public void ApplyPixelColors() {
        List<Color> list = new ArrayList<>();
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                list.add(PixelColors[j][i]);
            }
        }

        int[] rgbArr = new int[list.size()];
        for (int i = 0; i < rgbArr.length; i++) {
            rgbArr[i] = list.get(i).GetRGBInt();
        }

        _image.setRGB(0, 0, _width, _height, rgbArr, 0, _width);
    }

    public double FindMSE(Image encoded) {
        assert encoded._height == _height && encoded._width == _width;

        double cSum = 0.0;

        for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
                Color col = PixelColors[i][j];
                Color enCol = encoded.PixelColors[i][j];
                double rC = Math.pow(col.GetRed() - enCol.GetRed(), 2);
                double gC = Math.pow(col.GetGreen() - enCol.GetGreen(), 2);
                double bC = Math.pow(col.GetBlue() - enCol.GetBlue(), 2);

                cSum += rC + gC + bC;
            }
        }

        return cSum / _height * _width;
    }

    public void Show() {
        Show(_imageFilename);
    }

    public void Show(String title) {
        JFrame frame = new JFrame(title);
        ImagePanel imagePanel = new ImagePanel(_image);
        frame.add(imagePanel);
        frame.setSize(_width, _height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void SaveToFile(String filename) {
        try {
            ImageIO.write(_image, "jpg", new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
