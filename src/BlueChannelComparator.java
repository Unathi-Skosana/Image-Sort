/**
 * The program BlueChannelComparator inherits from the class Comparator to
 * construct a ColorMeanVector comparator that will compare the blue channel
 * value from the RGB values of two ColorMeanVector objects.
 *
 * @author Unathi Koketso Skosana
 * @version 1.0
 * @since 2017-27-02
 */

import java.util.Comparator;

public class BlueChannelComparator implements Comparator<ColorMeanVector> {
    public int compare(ColorMeanVector a, ColorMeanVector b) {
        Double meanA = new Double(a.getBlueChannelMean());
        Double meanB = new Double(b.getBlueChannelMean());
        return meanA.compareTo(meanB);
    }
}
