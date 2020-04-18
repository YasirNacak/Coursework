package Q1;

import Utilities.Image;

import java.io.*;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        int[] filterSizes = new int[] {
                5, 9, 11
        };

        int[] noisePercentages = new int[] {
                10, 25, 50, 75
        };

        TestImages("images_list", filterSizes, noisePercentages);
    }

    private static void TestImages(String tableOfContentsFilename, int[] filterSizes, int[] noisePercentages) {
        try {
            FileWriter fw = new FileWriter(new File("MSE_RESULTS.txt"));

            FileReader fr = new FileReader(new File(tableOfContentsFilename + ".txt"));
            BufferedReader br = new BufferedReader(fr);

            String filterResultsDirectoryName = tableOfContentsFilename + "_filter_results";

            File outDir = new File(filterResultsDirectoryName);
            outDir.mkdir();

            String imageFilename;
            while(true){
                try {
                    if ((imageFilename = br.readLine()) != null) {
                        for (int i = 0; i < filterSizes.length; i++) {
                            for (int j = 0; j < noisePercentages.length; j++) {
                                String imageOutputDirectoryName = filterResultsDirectoryName + "/" + imageFilename + "_NOISE_" + noisePercentages[j] + "_FILTERSIZE_" + filterSizes[i];
                                System.out.println(imageOutputDirectoryName);

                                outDir = new File(imageOutputDirectoryName);
                                outDir.mkdir();

                                Image inputImage = new Image("test_images" + "/" + imageFilename + ".jpg", false);
                                Image noisedInputImage = SaltAndPepperNoise.Apply(inputImage, noisePercentages[j]);
                                Image medianFilteredLex = MedianFilter.Apply(noisedInputImage, filterSizes[i], new LexicographicalComparator());
                                Image medianFilteredBmx = MedianFilter.Apply(noisedInputImage, filterSizes[i], new BitmixComparator());
                                Image medianFilteredNrm = MedianFilter.Apply(noisedInputImage, filterSizes[i], new NormBasedComparator());
                                Image medianFilteredMarginal = MedianFilter.Apply(noisedInputImage, filterSizes[i]);

                                inputImage.SaveToFile(imageOutputDirectoryName + "/" + imageFilename + "_1_ORIGINAL.jpg");
                                noisedInputImage.SaveToFile(imageOutputDirectoryName + "/" + imageFilename + "_2_NOISED.jpg");
                                medianFilteredLex.SaveToFile(imageOutputDirectoryName + "/" + imageFilename + "_3_V_LEX.jpg");
                                medianFilteredBmx.SaveToFile(imageOutputDirectoryName + "/" + imageFilename + "_4_V_BMX.jpg");
                                medianFilteredNrm.SaveToFile(imageOutputDirectoryName + "/" + imageFilename + "_5_V_NRM.jpg");
                                medianFilteredMarginal.SaveToFile(imageOutputDirectoryName + "/" + imageFilename + "_6_MARGINAL.jpg");

                                String outDesc = imageFilename + "_NOISE_" + noisePercentages[j] + "_FILTERSIZE_" + filterSizes[i];

                                BigDecimal lexMSE = new BigDecimal(inputImage.FindMSE(medianFilteredLex));
                                fw.write(outDesc + "_LEX" + "=" + lexMSE.toBigInteger() + "\n");

                                BigDecimal bmxMSE = new BigDecimal(inputImage.FindMSE(medianFilteredBmx));
                                fw.write(outDesc + "_BMX" + "=" + bmxMSE.toBigInteger() + "\n");

                                BigDecimal nrmMSE = new BigDecimal(inputImage.FindMSE(medianFilteredNrm));
                                fw.write(outDesc + "_NRM" + "=" + nrmMSE.toBigInteger() + "\n");

                                BigDecimal marMSE = new BigDecimal(inputImage.FindMSE(medianFilteredMarginal));
                                fw.write(outDesc + "_MAR" + "=" + marMSE.toBigInteger() + "\n");

                                fw.write("\n");
                            }
                        }
                    } else {
                        fw.close();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
