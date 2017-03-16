/**
 * The ComparatorQuick class provides static methods for sorting an
 * array and selecting the ith smallest element in an array using quicksort
 * It uses a comparator to specify order among the objects being sorted
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */

import java.util.Comparator;

public class ComparatorQuick {

  /**
     * Rearranges the array in ascending order, using the natural order.
     * @param objs     the array to be sorted
     * @param channel  comparator for specifying order
     */
    public static void sort(Object[] objs, Comparator channel) {
        sort(objs, channel, 0, objs.length - 1);
        assert isSorted(objs, channel);
    }


    // Quicksort the subarray from a[lo] to a[hi]
    private static void sort(Object[] a, Comparator channel, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, channel, lo, hi);
        sort(a, channel, lo, j-1);
        sort(a, channel, j+1, hi);
        assert isSorted(a, channel, lo, hi);
    }

    // partition the subarray objs[lo..hi] so that objs[lo..j-1] <= objs[j] <= objs[j+1..hi]
    // and return the index j
    private static int partition(Object[] objs, Comparator channel, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Object v = objs[lo];
        while (true) {
            while (less(objs[++i], v, channel))
                if (i == hi) break;
            while (less(v, objs[--j], channel))
                if (j == lo) break;
            if (i >= j) break;
            exch(objs, i, j);
        }
        exch(objs, lo, j);
        return j;
    }

   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    // is objA > objB ?
    private static boolean less(Object objA, Object objB, Comparator channel) {
        return channel.compare(objA, objB) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] objs, int i, int j) {
        Object swap = objs[i];
        objs[i] = objs[j];
        objs[j] = swap;
    }

   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/

    private static boolean isSorted(Object[] a, Comparator channel) {
        return isSorted(a, channel, 0, a.length - 1);
    }

    private static boolean isSorted(Object[] a, Comparator channel, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1], channel)) return false;
        return true;
    }
}
