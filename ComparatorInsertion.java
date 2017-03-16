/**
 * The class ComparatorInsertion provides static methods for sorting an array of
 * arbitrary objects using Insertion sort, instead of the objects having to implement
 * comparable, we use a comparator to sort.
 *
 * @author  Unathi Koketso Skosana
 * @version 1.0
 * @since   2017-27-02
 */

import java.util.Comparator;

public class ComparatorInsertion {

  /**
     * Rearranges array contents in ascending order, using a comparator
     * for comparison among the objects
     *
     * @param objs    The array to be sorted
     * @param channel A comparator for comparison among the objects
     */
  public static void sort(Object[] objs, Comparator channel) {
        int n = objs.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j > 0 && less(objs[j], objs[j-1], channel); j--) {
                exch(objs, j, j-1);
            }
            assert isSorted(objs, 0, i, channel);
        }
        assert isSorted(objs, channel);
    }



   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/

    // is objA > objB
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

    private static boolean isSorted(Object[] a, Comparator channel) {
        return isSorted(a, 0, a.length - 1, channel);
    }

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Object[] a, int lo, int hi, Comparator channel) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1], channel)) return false;
        return true;
    }
}
