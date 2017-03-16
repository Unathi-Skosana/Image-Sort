import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.imageio.ImageIO;
import java.io.File;

/**
 * @author Unathi Skosana
 * @version 1.0
 * @since 2017-27-02
 */
public class ImageSortGUI extends JFrame {
    private static final long serialVersionUID = -6145915057676192083L;
    private JPanel panelMain;
    private JPanel sortParamButtons;
    private JPanel importantButtons;
    private JPanel displayCanvas;
    private JPanel sortingAlgsButtons;
    private JPanel traverseButtons;
    private JPanel imageCanvas;
    private JPanel userText1;
    private JPanel userText2;

    private JRadioButton rRadioButton;
    private JRadioButton gRadioButton;
    private JRadioButton bRadioButton;
    private JRadioButton insertionRadioButton;
    private JRadioButton shellRadioButton;
    private JRadioButton mergeRadioButton;
    private JRadioButton quickRadioButton;
    private JRadioButton selectionRadioButton;

    public JButton resortButton;
    public JButton helpWindowButton;
    public JButton exitButton;
    public JButton firstButton;
    public JButton prevButton;
    public JButton nextButton;
    public JButton lastButton;

    private JLabel fileNameField;
    private JLabel sortingTime;
    private JLabel promptText2;
    private JLabel promptText1;

    private ButtonGroup buttonGroup1;
    private ButtonGroup buttonGroup2;

    private SortMediator sortUnit;
    private ArrayList<BufferedImage> sortedImages;
    private String imageDirectory;
    private int imageIndex = 0;

    public static void main(String[] args) throws Exception {
        String path = args[0];
        String sortAlg = args[1];
        String sortParam = args[2];
        String guiToggle = args[3];
        InputUnit inputUnit = new InputUnit(path, sortAlg, sortParam, guiToggle);
        JFrame frame = new JFrame("ImageSort");
        frame.setContentPane(new ImageSortGUI(inputUnit).panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Instantiates an instance of this type
     * and does all the preliminary stuff.
     *
     * @param inputUnit holds the data to be displayed.
     */
    public ImageSortGUI(InputUnit inputUnit) throws Exception {
        setUpLookAndFeel();
        $$$setupUI$$$();
        this.sortUnit = new SortMediator(inputUnit.getImageRGBMeans(),
                            inputUnit.getSortAlg(), inputUnit.getComparator());
        this.imageDirectory = inputUnit.getImageDirectory();
        this.sortedImages = buildBufferedImageList(sortUnit.getSortedList());
        checkButtons(inputUnit.getSortParam(), inputUnit.getSortAlg());
        displayOnCanvas();
        displaySortingTime();

        exitButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        helpWindowButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Make a help window pop-up
                JLabel label = new JLabel("<html>*Resort button<br>Resorts the images taking arguments"
                        + " from the checked buttons<br>*Help Window button<br>Opens this window<br>"
                            + "*Exit button<br>Exits the program</html>");
                label.setFont(new Font("Ubuntu", Font.PLAIN, 12));
                JOptionPane.showMessageDialog(null,label, "Help Message Dialog", JOptionPane.PLAIN_MESSAGE);
            }
        });

        resortButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String sortParam = getCheckedButtonText(buttonGroup1);
                String sortAlg = getCheckedButtonText(buttonGroup2);
                String newsortParam = convertRGBButtonText(sortParam);
                String newsortAlg = convertSortAlgButtonText(sortAlg);
                SortMediator newList = new SortMediator(sortUnit.getOriginalList(), newsortAlg, InputUnit.selectComparator(newsortParam));
                sortUnit = newList;
                displaySortingTime();

                try {
                    sortedImages = buildBufferedImageList(newList.getSortedList());
                } catch (IOException error) {
                    error.printStackTrace();
                }
                setImageIndexTo(0);
                displayOnCanvas();

            }
        });

        firstButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                setImageIndexTo(0);
                displayOnCanvas();
            }
        });

        prevButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIndexDecrement();
                displayOnCanvas();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIndexIncrement();
                displayOnCanvas();
            }
        });

        lastButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                setImageIndexTo(sortedImages.size() - 1);
                displayOnCanvas();
            }
        });
    }

    /**
     * Getter method
     *
     * @return returns the main JPanel.
     */
    public JPanel getMainPanel() {
        return panelMain;
    }

    /**
     * Getter method
     *
     * @return returns the text on the checked radio button
     */
    private String getCheckedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    /**
     * Sets up the Nimbus look and feel of the GUI.
     */
    private void setUpLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays current image in imageCanvas JPanel
     */
    private void displayOnCanvas() {
        imageCanvas.removeAll();
        BufferedImage picture = sortedImages.get(imageIndex);
        JLabel imageC = new JLabel(new ImageIcon(picture));
        displayFileName();
        imageCanvas.add(imageC);
        imageCanvas.repaint();
        imageCanvas.revalidate();
    }

    /**
     * Increments imageIndex
     */
    private void ImageIndexIncrement() {
        if (imageIndex < sortedImages.size() - 1) {
            ++imageIndex;
        }
    }

    /**
     * Decrements imageIndex
     */
    private void ImageIndexDecrement() {
        if (imageIndex > 0) {
            --imageIndex;
        }
    }

    /**
     * Sets imageIndex to specified index
     */
    private void setImageIndexTo(int index) {
        imageIndex = index;
    }

    /**
     * Converts the text on a RGB button to text
     * that is useful
     *
     * @return returns converted text
     */
    private String convertRGBButtonText(String buttonText) {
        switch (buttonText) {
            case "R":
                return "0";
            case "G":
                return "1";
            case "B":
                return "2";
        }
        throw new ImageSortException();
    }

    /**
     * Converts the text on a sorting algorithm
     * to text that is useful
     *
     * @return returns converted text
     */
    private String convertSortAlgButtonText(String buttonText) {
        switch (buttonText) {
            case "Insertion":
                return "0";
            case "Shell":
                return "1";
            case "Merge":
                return "2";
            case "Quick":
                return "3";
            case "Selection":
                return "4";
        }
        throw new ImageSortException();
    }

    /**
     * Builds an arrayList of bufferedImages from the
     * array of ColorMeanVector Objects
     *
     * @return returns the built arrayList
     * @throws IOException if an input or output error
     *                     occurs.
     */
    private ArrayList<BufferedImage> buildBufferedImageList(ColorMeanVector[] means) throws IOException {
        ArrayList<BufferedImage> sortedList = new ArrayList<BufferedImage>();
        for (int i = 0; i < means.length; i++) {
            BufferedImage p = ImageIO.read(new File(imageDirectory + means[i].getImageName()));
            sortedList.add(p);
        }
        return sortedList;
    }

    /**
     * Checks buttons beforehand
     */
    private void checkButtons(String sortParam, String sortAlg) {
        switch (sortParam) {
            case "0":
                rRadioButton.setSelected(true);
                break;
            case "1":
                gRadioButton.setSelected(true);
                break;
            case "2":
                bRadioButton.setSelected(true);
                break;
        }

        switch (sortAlg) {
            case "0":
                insertionRadioButton.setSelected(true);
                break;
            case "1":
                shellRadioButton.setSelected(true);
                break;
            case "2":
                mergeRadioButton.setSelected(true);
                break;
            case "3":
                quickRadioButton.setSelected(true);
                break;
            case "4":
                selectionRadioButton.setSelected(true);
                break;
        }
    }

    /**
     * prints file name of the currently displayed image
     * above the image.
     */
    private void displayFileName() {
        String fileName = (sortUnit.getSortedList()[imageIndex].getImageName());
        fileNameField.setText(fileName);
    }

    /**
     * prints the sorting time of the current configuration
     * below the image
     */
    private void displaySortingTime() {
        sortingTime.setText("Sorting time \n" + sortUnit.getSortingTime() + " s");
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelMain.setFont(new Font("Ubuntu Mono", Font.PLAIN, panelMain.getFont().getSize()));
        displayCanvas = new JPanel();
        displayCanvas.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(displayCanvas);
        userText1 = new JPanel();
        userText1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        displayCanvas.add(userText1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(233, 5), null, 0, false));
        promptText2 = new JLabel();
        promptText2.setFont(new Font("Ubuntu", Font.PLAIN, promptText2.getFont().getSize()));
        promptText2.setText("Pick a color");
        userText1.add(promptText2);
        sortParamButtons = new JPanel();
        sortParamButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        displayCanvas.add(sortParamButtons, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(233, 5), null, 0, false));
        rRadioButton = new JRadioButton();
        rRadioButton.setFont(new Font("Ubuntu", Font.PLAIN, rRadioButton.getFont().getSize()));
        rRadioButton.setText("R");
        sortParamButtons.add(rRadioButton);
        gRadioButton = new JRadioButton();
        gRadioButton.setFont(new Font("Ubuntu", Font.PLAIN, gRadioButton.getFont().getSize()));
        gRadioButton.setText("G");
        sortParamButtons.add(gRadioButton);
        bRadioButton = new JRadioButton();
        bRadioButton.setFont(new Font("Ubuntu", Font.PLAIN, bRadioButton.getFont().getSize()));
        bRadioButton.setText("B");
        sortParamButtons.add(bRadioButton);
        sortingAlgsButtons = new JPanel();
        sortingAlgsButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        displayCanvas.add(sortingAlgsButtons, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(233, 5), null, 0, false));
        insertionRadioButton = new JRadioButton();
        insertionRadioButton.setFont(new Font("Ubuntu", Font.PLAIN, insertionRadioButton.getFont().getSize()));
        insertionRadioButton.setText("Insertion");
        sortingAlgsButtons.add(insertionRadioButton);
        shellRadioButton = new JRadioButton();
        shellRadioButton.setFont(new Font("Ubuntu", Font.PLAIN, shellRadioButton.getFont().getSize()));
        shellRadioButton.setText("Shell");
        sortingAlgsButtons.add(shellRadioButton);
        mergeRadioButton = new JRadioButton();
        mergeRadioButton.setFont(new Font("Ubuntu", Font.PLAIN, mergeRadioButton.getFont().getSize()));
        mergeRadioButton.setText("Merge");
        sortingAlgsButtons.add(mergeRadioButton);
        quickRadioButton = new JRadioButton();
        quickRadioButton.setFont(new Font("Ubuntu", Font.PLAIN, quickRadioButton.getFont().getSize()));
        quickRadioButton.setText("Quick");
        sortingAlgsButtons.add(quickRadioButton);
        selectionRadioButton = new JRadioButton();
        selectionRadioButton.setFont(new Font("Ubuntu", Font.PLAIN, selectionRadioButton.getFont().getSize()));
        selectionRadioButton.setText("Selection");
        sortingAlgsButtons.add(selectionRadioButton);
        userText2 = new JPanel();
        userText2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        displayCanvas.add(userText2, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(233, 20), null, 0, false));
        promptText1 = new JLabel();
        promptText1.setFont(new Font("Ubuntu", Font.PLAIN, promptText1.getFont().getSize()));
        promptText1.setText("Pick a sorting algorithm");
        userText2.add(promptText1);
        imageCanvas = new JPanel();
        imageCanvas.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        displayCanvas.add(imageCanvas, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(250, 250), new Dimension(233, 10), new Dimension(250, 250), 0, false));
        fileNameField = new JLabel();
        fileNameField.setEnabled(true);
        fileNameField.setFont(new Font("Ubuntu", Font.PLAIN, fileNameField.getFont().getSize()));
        fileNameField.setText("");
        displayCanvas.add(fileNameField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(0, 0), null, 0, false));
        sortingTime = new JLabel();
        sortingTime.setFont(new Font("Ubuntu", Font.PLAIN, sortingTime.getFont().getSize()));
        sortingTime.setText("");
        displayCanvas.add(sortingTime, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(0, 0), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), 0, 0));
        displayCanvas.add(panel1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        traverseButtons = new JPanel();
        traverseButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(traverseButtons, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(1, 1), null, 0, false));
        firstButton = new JButton();
        firstButton.setFont(new Font("Ubuntu", Font.PLAIN, firstButton.getFont().getSize()));
        firstButton.setText("first");
        traverseButtons.add(firstButton);
        prevButton = new JButton();
        prevButton.setFont(new Font("Ubuntu", Font.PLAIN, prevButton.getFont().getSize()));
        prevButton.setText("prev");
        traverseButtons.add(prevButton);
        nextButton = new JButton();
        nextButton.setFont(new Font("Ubuntu", Font.PLAIN, nextButton.getFont().getSize()));
        nextButton.setText("next");
        traverseButtons.add(nextButton);
        lastButton = new JButton();
        lastButton.setFont(new Font("Ubuntu", Font.PLAIN, lastButton.getFont().getSize()));
        lastButton.setText("last");
        traverseButtons.add(lastButton);
        importantButtons = new JPanel();
        importantButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(importantButtons, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        resortButton = new JButton();
        resortButton.setFont(new Font("Ubuntu", Font.PLAIN, resortButton.getFont().getSize()));
        resortButton.setText("Resort");
        importantButtons.add(resortButton);
        helpWindowButton = new JButton();
        helpWindowButton.setFont(new Font("Ubuntu", Font.PLAIN, helpWindowButton.getFont().getSize()));
        helpWindowButton.setText("Help Window");
        importantButtons.add(helpWindowButton);
        exitButton = new JButton();
        exitButton.setFont(new Font("Ubuntu", Font.PLAIN, exitButton.getFont().getSize()));
        exitButton.setText("Exit");
        importantButtons.add(exitButton);
        buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(rRadioButton);
        buttonGroup1.add(gRadioButton);
        buttonGroup1.add(bRadioButton);
        buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(insertionRadioButton);
        buttonGroup2.add(shellRadioButton);
        buttonGroup2.add(mergeRadioButton);
        buttonGroup2.add(quickRadioButton);
        buttonGroup2.add(selectionRadioButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }
}
