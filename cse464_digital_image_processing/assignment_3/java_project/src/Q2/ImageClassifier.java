package Q2;

import Utilities.Image;

import java.io.*;
import java.util.*;

public class ImageClassifier {
    public ArrayList<ImageClass> ImageClasses;
    public ArrayList<ClassifiedImage> TestData;
    public int K;
    public int TotalClassifyCount;
    public int CorrectClassifyCount;
    public float SuccessPercentage;

    public ImageClassifier(int k, String imageClassesFilePath, String testDataFilePath, String trainDataFilePath) {
        TotalClassifyCount = 0;
        CorrectClassifyCount = 0;
        this.K = k;
        ImageClasses = new ArrayList<>();
        TestData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(imageClassesFilePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split("\\s+");
                if(splitted.length > 1) {
                    ImageClasses.add(new ImageClass(Integer.parseInt(splitted[1]), splitted[0]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(new File(testDataFilePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split("\\s+");
                if(splitted.length > 1) {
                    ImageClass ic = null;

                    for (int i = 0; i < ImageClasses.size(); i++) {
                        if(splitted[1].equals(String.valueOf(ImageClasses.get(i).ClassIndex))) {
                            ic = ImageClasses.get(i);
                        }
                    }

                    String parentPath = testDataFilePath.substring(0, testDataFilePath.lastIndexOf("/"));

                    System.out.println("Loading: " + parentPath + "/../images/" + splitted[0]);

                    Image img = new Image(parentPath + "/../images/" + splitted[0], true);
                    int[] fVector = DipMethods.CalculateLBPHistogram(img);

                    TestData.add(new ClassifiedImage(ic, img, fVector));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(new File(trainDataFilePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split("\\s+");
                if(splitted.length > 1) {
                    System.out.println("Classifying: " + splitted[0]);

                    String parentPath = testDataFilePath.substring(0, testDataFilePath.lastIndexOf("/"));

                    Image img = new Image(parentPath + "/../images/" + splitted[0], true);

                    Integer correctClass = Integer.parseInt(splitted[1]);

                    ImageClass classifiedClass = Classify(img);

                    if(correctClass.equals(classifiedClass.ClassIndex)) {
                        System.out.println("OK.");
                        CorrectClassifyCount++;
                    }
                    else{
                        System.out.println("NOT OK.");
                    }

                    TotalClassifyCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Success: " + CorrectClassifyCount + "/" + TotalClassifyCount);
        SuccessPercentage = (float)CorrectClassifyCount / (float)TotalClassifyCount * 100f;
        System.out.println("Percentage: " + SuccessPercentage);
    }

    public ImageClass Classify(Image source) {
        TreeMap<Double, ClassifiedImage> distances = new TreeMap<>();

        int[] fVector = DipMethods.CalculateLBPHistogram(source);

        for (int i = 0; i < TestData.size(); i++) {
            double dist = 0;

            for (int j = 0; j < 256; j++) {
                dist += Math.pow(fVector[j] - TestData.get(i).FVector[j], 2);
            }

            dist = Math.sqrt(dist);

            distances.put(dist, TestData.get(i));
        }

        int counterToK = 0;

        HashMap<ImageClass, Integer> classCount = new HashMap<>();

        for (Map.Entry<Double, ClassifiedImage> entry : distances.entrySet()) {
            if(classCount.containsKey(entry.getValue().ImageClass)) {
                classCount.put(entry.getValue().ImageClass, classCount.get(entry.getValue().ImageClass) + 1);
            } else {
                classCount.put(entry.getValue().ImageClass, 1);
            }

            counterToK++;
            if(counterToK == K) break;
        }

        ImageClass winnerClass = null;
        int maxCount = -1;
        for (ImageClass k : classCount.keySet()) {
            if(classCount.get(k) > maxCount) {
                maxCount = classCount.get(k);
                winnerClass = k;
            }
        }

        return winnerClass;
    }
}
