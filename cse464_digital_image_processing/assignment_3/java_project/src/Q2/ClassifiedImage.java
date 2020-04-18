package Q2;

import Utilities.Image;

public class ClassifiedImage {
    public ImageClass ImageClass;
    public Image Image;
    public int[] FVector;

    public ClassifiedImage(ImageClass imageClass, Image image, int[] fVector) {
        this.ImageClass = imageClass;
        this.Image = image;
        this.FVector = fVector;
    }
}
