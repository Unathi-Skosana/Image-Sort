/**
 * RedChannelComparator inherits from the class Comparator to
 * construct a ColorMeanVector comparator that will compare the red channel
 * value from the RGB values of two ColorMeanVector objects.
 *
 * @author Unathi Koketso Skosana
 * @version 1.0
 * @since 2017-27-02
 */

import java.util.Comparator;

public class RedChannelComparator implements Comparator<ColorMeanVector> {
    public int compare(ColorMeanVector a, ColorMeanVector b) {
        Double meanA = new Double(a.getRedChannelMean());
        Double meanB = new Double(b.getRedChannelMean());
        return meanA.compareTo(meanB);
    }
}
