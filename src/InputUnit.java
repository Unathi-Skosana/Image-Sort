/**
 * The program InputUnit implements an input proccessing unit for ImageSort,
 * handling most of the input related computations
 *
 * @author  Unathi Koketso Skosana
 * @version 1.0
 * @since   2017-27-02
 */

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.RuntimeException;
import java.util.ArrayList;
import java.util.Comparator;
import javax.imageio.ImageIO;

public class InputUnit {
    private ArrayList<File> imageFiles;
    private ColorMeanVector[] means;
    private Comparator<ColorMeanVector> comparisonChannel;
    private String imageDirectory;
    private String guiState;
    private String sortAlg;
    private String sortParam;

    public InputUnit(String imageDirectory, String sortAlg
                        , String sortParam, String guiToggle) {
        inspectInput(sortAlg, sortParam, guiToggle);
        this.imageFiles = getFilesByExt(imageDirectory, "jpg");
        this.comparisonChannel = selectComparator(sortParam);
        this.means = calculateMeans(imageFiles);
        this.imageDirectory = imageDirectory;
        this.sortAlg = sortAlg;
        this.sortParam = sortParam;
        this.guiState = guiToggle;
    }

    /**
     * Getter method
     *
     * @return returns the RGB channel comparator
     */
    public Comparator<ColorMeanVector> getComparator() {
        return comparisonChannel;
    }

    /**
     * Getter method
     *
     * @return returns the ColorMeanVector objects associated
     *         with each image
     */

    public ColorMeanVector[] getImageRGBMeans() {
        return means;
    }

    /**
     * Getter method
     *
     * @return returns the state of the graphical user interface
     */

    public String getGUIState() {
        return guiState;
    }

    /**
     * Getter method
     *
     * @return returns the sorting algorithm
     */
    public String getSortAlg() {
        return sortAlg;
    }

    /**
     * Getter method
     *
     * @return returns the sorting parameter
     */

    public String getSortParam() {
        return sortParam;
    }

    /**
     *  Getter method
     *
     * @return returns the current image directory.
     **/

    public String getImageDirectory() {
        return imageDirectory;
    }

    /**
     * Selects a RGB channel comparator to be used as a sorting criteria
     *
     * @param  digit             a single digit from 0 to 2 that correspond to rgb
     *                           channels
     * @return                   ChannelComparator that corresponds to a rgb channel
     * @throws RuntimeException  if digit is out of the range [0,2]
     */
    public static Comparator<ColorMeanVector> selectComparator(String digit) {
        switch (digit) {
            case "0":
                return new RedChannelComparator();
            case "1":
                return new GreenChannelComparator();
            case "2":
                return new BlueChannelComparator();
        }
        throw new ImageSortException();
    }

    /**
     * An error handling method that checks for any sort of wayward
     * input parsed to instatiate an InputUnit object.
     *
     * @param  sortAlg            sorting algorithm
     * @param  sortParam          sorting parameter
     * @param  guiState           GUI state
     * @throws ImageSortException if an error for invalid command line
     *                            input
     */
    private static void inspectInput(String sortAlg
                , String sortParam, String guiState) {
        if (!(sortAlg.equals("0")
               || sortAlg.equals("1")
               || sortAlg.equals("2")
               || sortAlg.equals("3")
               || sortAlg.equals("4"))) {
            throw new ImageSortException("An input error occured:"
                        +  " Invalid sorting algorithm choice.");
        }

        if (!(sortParam.equals("0") 
                || sortParam.equals("1")
                || sortParam.equals("2"))) {
            throw new ImageSortException("An input error occured:" 
                        + " Invalid sorting parameter choice.");
        }

        if (!(guiState.equals("0")
                || guiState.equals("1"))) {
           throw new ImageSortException("An input error occured:"
                        + " Invalid GUI state choice.");
        }
    }

    /**
     * invokes calculateMean() to calculate the RGB channel averages of a
     * multiple images and constructs an array of ColorMeanVector
     * objects returned from calculateMean()
     *
     * @param  imageFiles arraylist containing images
     * @return returns an array of ColorMeanVector objects
     */
    private static ColorMeanVector[] calculateMeans(ArrayList<File> imageFiles) {
        ColorMeanVector[] colorVectors = new ColorMeanVector[imageFiles.size()];
        for (int i = 0; i < imageFiles.size(); i++) {
            colorVectors[i] = calculateMean(imageFiles.get(i));
        }
        return colorVectors;
    }

   /**
     * calculate the RGB channel averages and populates the values into a
     * ColorMeanVector object along with the name of the file
     *
     * @param file jpg image parsed as a file.
     * @return returns a ColorMeanVector object corresponding to an image.
     */
    private static ColorMeanVector calculateMean(File file) {
        int r = 0;
        int g = 0;
        int b = 0;
        int pixelCount = 0;
        try {
            BufferedImage p = ImageIO.read(file);
            for (int i = 0; i < p.getHeight(); i++) {
                for (int j = 0; j < p.getWidth(); j++) {
                    Color currentPixel = new Color(p.getRGB(j, i));
                    r += currentPixel.getRed();
                    g += currentPixel.getGreen();
                    b += currentPixel.getBlue();
                    pixelCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ColorMeanVector(file.getName()
                , 1.00 * r / pixelCount
                , 1.00 * g / pixelCount
                , 1.00 * b / pixelCount);
    }

    /**
     * Filters files in directory by file extension
     *
     * @param  directory directory to look files in
     * @param  extension filter file extension
     * @return returns arrayList containing the filtered files.
     */
    private static ArrayList<File>
            getFilesByExt(String directory, String extension) {
        ArrayList <File> images = new ArrayList<File>();
        File[] files = new File(directory).listFiles();
        for (File file: files) {
            if (file.isFile()
                && isExtension(file.getName(), extension)) {
                images.add(file);
            }
        }
        return images;
    }

    /**
     * Helper function to check if a file name
     * is of a certain extension
     *
     * @param  fileName   name of file
     * @param  extension  file extension
     * @return returns a true if fileName has the extension, and returns
     *         false if otherwise.
     */
    private static boolean isExtension(String fileName
                , String extension) {
        int index = fileName.indexOf(".");
        return extension.equals(fileName.substring(index + 1
                , fileName.length()));
    }
}
