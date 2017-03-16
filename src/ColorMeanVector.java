/**
 * The program ColorMeanVector implements an abstraction of an image feature vector
 * in this case the feature is the RGB mean value associated with each
 * image
 *
 * @author  Unathi Koketso Skosana
 * @version 1.0
 * @since   2017-27-02
 */

public class ColorMeanVector {
    private final double red;
    private final double green;
    private final double blue;
    private final String imageName;

    /**
     * Constructor, instantiates a ColorMeanVector with the properties
     * parsed as arguments
     *
     * @param ImageName name of image
     * @param red       red channel mean
     * @param green     green channel mean
     * @param blue      blue channel mean
     */

    public ColorMeanVector(String imageName, double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.imageName = imageName;
    }

    /**
     * Self explanatory
     *
     * @return red  returns red channel mean
     */
    public double getRedChannelMean() {
        return red;
    }

    /**
     * Self explanatory
     *
     * @return green  returns green channel mean
     */
    public double getGreenChannelMean() {
        return green;
    }

    /**
     * Self explanatory
     *
     * @return blue  returns the blue channel mean
     */
    public double getBlueChannelMean() {
        return blue;
    }

    /**
     * Self explanatory
     *
     * @return imageName  name of image
     */
    public String getImageName() {
        return imageName;
    }

}
