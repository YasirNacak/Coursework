package Q2;

import Utilities.Image;

public class Main {
    public static void main(String[] args) {
        ImageClassifier imageClassifier = new ImageClassifier(4,
                "Outex_TC_00012/000/classes.txt",
                "Outex_TC_00012/000/train.txt",
                "Outex_TC_00012/000/test.txt");

        ImageClassifier imageClassifier2 = new ImageClassifier(4,
                "Outex_TC_00012/001/classes.txt",
                "Outex_TC_00012/001/train.txt",
                "Outex_TC_00012/001/test.txt");

        System.out.println("Outex_TC_00012/000 Success Percentage: " + imageClassifier.SuccessPercentage);
        System.out.println("Outex_TC_00012/001 Success Percentage: " + imageClassifier2.SuccessPercentage);

        System.out.println("Program Finished.");
    }
}

/**
 * small notes to the kid who programmed this
 * Without EQ:
 * 000 Success: 51.944447%
 * 001 Success: 53.171295%
 *
 * With EQ:
 * 000 Success: 51.944447%
 * 001 Success: 54.18981%
 *
 * more radius is not always the better
 * there is a fine line
 *
 * couldnt test more points
 *
 * couldnt granulometry
 **/