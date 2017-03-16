 /**
  * ImageSort implements a program that sorts images according the means of
  * their rgb values and outputs the sorted list of image names to a text
  * file depending on one of its arguments, invokes a graphical user interface
  *
  * @author Unathi Koketso Skosana
  * @version 1.0
  * @since 2016-02-27
  */

import javax.swing.JFrame;

public class ImageSort {
    public static void main(String[] args) throws Exception {
        // Command-line arguments
        String path        = args[0];
        String sortAlg     = args[1];
        String sortParam   = args[2];
        String guiToggle   = args[3];
        InputUnit input    = new InputUnit(path, sortAlg, sortParam, guiToggle);
        SortMediator sortMediator = new SortMediator(input.getImageRGBMeans(), input.getSortAlg(), input.getComparator());
        sortMediator.writeImageNamesToFile();
        checkGUIInvocation(guiToggle, input);
    }

    /**
     * Decides whether to invoke the graphical
     * user interface or not.
     *
     * @param guiState    0 - off
     *                    1 - on
     * @throws Exception  if digit is neither a 0 or 1
     */
    public static void checkGUIInvocation(String guiState, InputUnit inputUnit) throws Exception {
        if (guiState.equals("1")) {
            invokeGUI(inputUnit);
        }
    }

    /**
     * invokes the graphical user interface
     *
     * @param inputUnit an instance of InputUnit that
     *        holds the data to displayed.
     */

    private static void invokeGUI(InputUnit inputUnit) throws Exception {
        JFrame frame = new JFrame("ImageSortGUI");
        frame.setContentPane(new ImageSortGUI(inputUnit).$$$getRootComponent$$$());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

   }
