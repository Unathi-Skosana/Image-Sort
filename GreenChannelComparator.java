/**
 * The program GreenChannelComparator inherits from the class Comparator to
 * construct a ColorMeanVector comparator that will compare the green channel
 * value from the RGB values of two ColorMeanVector objects.
 *
 * @author Unathi Koketso Skosana
 * @version 1.0
 * @since 2017-27-02
 */

import java.util.Comparator;

public class GreenChannelComparator implements Comparator<ColorMeanVector> {
    public int compare(ColorMeanVector a, ColorMeanVector b) {
        Double meanA = new Double(a.getGreenChannelMean());
        Double meanB = new Double(b.getGreenChannelMean());
        return meanA.compareTo(meanB);
    }
}
