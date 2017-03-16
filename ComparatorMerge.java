/**
 * The class ComparatorMerge provides static methods for sorting an array of
 * arbitrary objects using mergesort, using a comparator for specifying order
 * among the objects.
 * 
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */

import java.util.Comparator;

public class ComparatorMerge {

    // Stably merge to subarrays of arbitrary objects using an auxilary array.
    private static void merge(Object[] objs, Object[] aux, Comparator channel, int lo, int mid, int hi) {
        assert isSorted(objs, channel, lo, mid);
        assert isSorted(objs, channel, mid+1, hi);
        for (int k = lo; k <= hi; k++) {
            aux[k] = objs[k];
        }
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              objs[k] = aux[j++];
            else if (j > hi)               objs[k] = aux[i++];
            else if (less(aux[j], aux[i], channel)) objs[k] = aux[j++];
            else                           objs[k] = aux[i++];
        }
        assert isSorted(objs, channel, lo, hi);
    }

    // Mergesort an array of objects using an auxilary array.
    private static void sort(Object[] objs, Object[] aux, Comparator channel, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(objs, aux, channel, lo, mid);
        sort(objs, aux, channel,mid + 1, hi);
        merge(objs, aux ,channel , lo, mid, hi);
    }

    /**
     * Sorts arbitrary array of objectsin ascending order
     *
     * @param objs    array to be sorted
     * @param channel the comparator for specifying 
     */
    public static void sort(Object[] objs, Comparator channel) {
        Object[] aux = new Object[objs.length];
        sort(objs, aux, channel, 0, objs.length-1);
        assert isSorted(objs, channel);
    }


   /***************************************************************************
    *  Helper sorting function.
    ***************************************************************************/

    // is objA < objB ?
    private static boolean less(Object objA, Object objB, Comparator channel) {
        return channel.compare(objA, objB) < 0;
    }

   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/

    private static boolean isSorted(Object[] objs, Comparator channel) {
        return isSorted(objs, channel, 0, objs.length - 1);
    }

    private static boolean isSorted(Object[] objs, Comparator channel, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(objs[i], objs[i-1], channel)) return false;
        return true;
    }
}
