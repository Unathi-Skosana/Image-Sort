/**
 *  The Shell class provides static methods for sorting an
 *  array using Shellsort with Knuth's increment sequence (1, 4, 13, 40, ...)
 *  specifying the order of the objects using a comparator
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

import java.util.Comparator;

public class ComparatorShell {

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param objs    the array to be sorted
     * @param channel comparator for specifying order
     */
    public static void sort(Object[] objs, Comparator channel) {
        int n = objs.length;
        int h = 1;
        while (h < n/3) h = 3*h + 1;
        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(objs[j], objs[j-h], channel); j -=h) {
                    exch(objs, j, j-h);
                }
            }
            assert isHsorted(objs, channel, h);
            h /= 3;
        }
        assert isSorted(objs, channel);
    }

   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/

    // is objA < objB
    private static boolean less(Object objA, Object objB, Comparator channel) {
        return channel.compare(objA, objB) < 0;
    }

    // exchange objs[i] and objs[j]
    private static void exch(Object[] objs, int i, int j) {
        Object swap = objs[i];
        objs[i] = objs[j];
        objs[j] = swap;
    }

   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/

    private static boolean isSorted(Object[] objs, Comparator channel) {
        for (int i = 1; i < objs.length; i++)
            if (less(objs[i], objs[i-1], channel)) return false;
        return true;
    }

    private static boolean isHsorted(Object[] objs, Comparator channel, int h) {
        for (int i = h; i < objs.length; i++)
            if (less(objs[i], objs[i-h], channel)) return false;
        return true;
    }
}
