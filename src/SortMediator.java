/**
 * SortMediator implements a program that reinforces
 * data organisation and handles all the nitty gritty
 * related to sorting
 *
 * @author  Unathi Koketso Skosana
 * @version 1.0
 * @since   2017-27-02
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

public class SortMediator {
    private ColorMeanVector[] originalList;
    private ColorMeanVector[] sortedList;
    private double sortingTime;

    /**
     * constructor instatiates an instance of this type
     * makes a defensive copy of the array parsed,
     * sorts an array of ColorMeanVector object and then records the
     * time it took
     *
     * @param means             array of ColorMeanVector objects
     * @param SortAlg           sorting algorithm
     * @param comparisonChannel comparator for specifying order.
     */
    public SortMediator(ColorMeanVector[] means
                , String sortAlg
                , Comparator<ColorMeanVector> comparisonChannel) {
        this.originalList = copyArray(means);
        long start = System.currentTimeMillis();
        sort(means, sortAlg, comparisonChannel);
        long end = System.currentTimeMillis();
        this.sortingTime = (end - start)/1000.00;
        this.sortedList =  means;
    }


    /**
     * Getter method
     *
     * @return returns original and unsorted list
     */
    public ColorMeanVector[] getOriginalList() {
        return originalList;
    }

    /**
     * Getter method
     *
     * @return returns the sorted list
     */
    public ColorMeanVector[] getSortedList() {
        return sortedList;
    }

    /**
     * Getter method
     *
     * @return returns the sorting time
     */
    public double getSortingTime() {
        return sortingTime;
    }

    /**
     * Writes the sorted image names of the ColorMeanVector objects to
     * a sorted.txt
     *
     * @param means        array of ColorMeanVector objects that correspond to
     *                     images.
     * @throws IOException if input or output exception occurred.
     */
    public void writeImageNamesToFile() throws Exception {
        FileWriter imageList = new FileWriter("sorted.txt");
        for (ColorMeanVector mean: sortedList) {
            imageList.write(mean.getImageName() +"\n");
        }
        imageList.close();
    }

   /**
     * sorts the array parsed as an arguments with specified sort
     * algorithm, using a comparator to specify order among the
     * objects being sorted.
     *
     * @param means   array to be sorted
     * @param sortAlg sorting algorithm
     * @param channel comparator for specifying order.
     */
    private static void sort(ColorMeanVector[] means
                , String sortAlg
                , Comparator<ColorMeanVector> channel) {
        switch (sortAlg) {
            case "0":
                ComparatorInsertion.sort(means, channel);
                break;
            case "1":
                ComparatorShell.sort(means, channel);
                break;
            case "2":
                ComparatorMerge.sort(means, channel);
                break;
            case "3":
                ComparatorQuick.sort(means, channel);
                break;
            case "4":
                ComparatorSelection.sort(means, channel);
                break;
            default:
                throw new ImageSortException();
        }
    }

   /**
     * Makes a defensive copy of an array
     *
     * @param  means array to be copied.
     * @return returns a copy
     */
    private ColorMeanVector[] copyArray(ColorMeanVector[] means) {
        ColorMeanVector[] copy = new ColorMeanVector[means.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = means[i];
        }
        return copy;
    }
}
