/**
 *  The Selection class provides static methods for sorting an
 *  array using selection sort
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

import java.util.Comparator;

public class ComparatorSelection {
    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param objs    the array to be sorted
     * @param channel the comparator specifying the order among objects
     */
    public static void sort(Object[] objs, Comparator channel) {
        int n = objs.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i+1; j < n; j++) {
                if(less(objs[j], objs[min], channel)) min = j;
            }
            exch(objs, i, min);
            assert isSorted(objs, channel, 0, i);
        }
        assert isSorted(objs, channel);
    }

   /***************************************************************************
    **  Helper sorting functions.
    ****************************************************************************/

    // is objA < objB ?
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

    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    private static boolean isSorted(Object[] a, Comparator channel, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1], channel)) return false;
        return true;
    }
}
