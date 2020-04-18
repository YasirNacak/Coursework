package Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private BufferedImage _image;

    public ImagePanel(BufferedImage image) {
        this._image = image;
        this.setPreferredSize(new Dimension(_image.getWidth(), _image.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(_image, 0, 0, this);
    }
}
