import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Comparator;

import javax.swing.JFrame;

public class ImageSortTester {  
    private  ColorMeanVector objA;
    private ColorMeanVector objB;
    private InputUnit validInput;
    private ImageSortGUI gui;

    public void setUp() throws Exception {
    	objA       = new ColorMeanVector("BhekiMpilo.jpg", 20, 100, 200); // Artificial testing
    	objB       = new ColorMeanVector("Unathi.jpg", 10, 200, 200);     // Artificial testing
    	validInput = new InputUnit("tests/", "0", "0", "1");              // Valid InputUnit object
    	gui = new ImageSortGUI(validInput);
    }

    @Test
	public void test() throws Exception {
		// Setup
		setUp();
		
		// ColorMeanVector tests
		testImageName(objA);
		testBlueChannelMean(objA);
		testRedChannelMean(objA);
		testGreenChannelMean(objA);

		// Comparator tests
		testBlueChannelComparator(objA, objB);
		testRedChannelComparator(objA, objB);
		testGreenChannelComparator(objA, objB);

		// InputUnit tests
		testInputUnitGUIState(validInput);
		testInputUnitInvalidSortParamChoice();
		testInputUnitInvalidSortAlgChoice();
		testInputUnitInvalidGUIStateChoice();

		// ImageSortException test
		testImageSortExceptionNonDefaultConstructor();
		testImageSortExceptionDefaultConstructor();

		// ImageSort tests with the GUI off
		ImageSort.main(new String[] {"tests/","0","0","0"});
		ImageSort.main(new String[] {"tests/","1","0","0"});
		ImageSort.main(new String[] {"tests/","2","1","0"});
		ImageSort.main(new String[] {"tests/","3","2","0"});
		ImageSort.main(new String[] {"tests/","4","0","0"});

		// ImageSort tests with the GUI on
		ImageSort.main(new String[] {"tests/","0","0","1"});
		ImageSort.main(new String[] {"tests/","1","0","1"});
		ImageSort.main(new String[] {"tests/","2","1","1"});
		ImageSort.main(new String[] {"tests/","3","2","1"});
		ImageSort.main(new String[] {"tests/","4","0","1"});

		//ImageSortGUI tests with GUI off
		ImageSortGUI.main(new String[] {"tests/","0","0","0"});
		ImageSortGUI.main(new String[] {"tests/","1","0","0"});
		ImageSortGUI.main(new String[] {"tests/","2","1","0"});
		ImageSortGUI.main(new String[] {"tests/","3","2","0"});
		ImageSortGUI.main(new String[] {"tests/","4","0","0"});

		//ImageSortGUI tests with GUI on
		ImageSortGUI.main(new String[] {"tests/","0","0","1"});
		ImageSortGUI.main(new String[] {"tests/","1","0","1"});
		ImageSortGUI.main(new String[] {"tests/","2","1","1"});
		ImageSortGUI.main(new String[] {"tests/","3","2","1"});
		ImageSortGUI.main(new String[] {"tests/","4","0","1"});
		
		JFrame frame = new JFrame("ImageSort");
		frame.setContentPane(gui.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    	gui.firstButton.doClick();
    	gui.lastButton.doClick();
    	gui.nextButton.doClick();
    	gui.prevButton.doClick();
    	gui.helpWindowButton.doClick();
    	gui.resortButton.doClick();    	
    	GridConstraints tester = new GridConstraints();
    	tester.setColumn(2);
    	tester.setRow(10);
    	tester.setAnchor(0);
    	tester.setCell(true, 0);
    	tester.setFill(0);
    	tester.getAnchor();
    	tester.getFill();
    	tester.getRow();
		
    }

	private void testBlueChannelComparator(ColorMeanVector objA, ColorMeanVector objB) {
		Comparator<ColorMeanVector> channel = new BlueChannelComparator();
		assertEquals(0, channel.compare(objA, objB));
	}

	private void testRedChannelComparator(ColorMeanVector objA, ColorMeanVector objB) {
		Comparator<ColorMeanVector> channel = new RedChannelComparator();
		assertEquals(1, channel.compare(objA, objB));
	}

	private void testGreenChannelComparator(ColorMeanVector objA, ColorMeanVector objB) {
		Comparator<ColorMeanVector> channel = new GreenChannelComparator();
		assertEquals(-1, channel.compare(objA, objB));
	}

	private void testImageName(ColorMeanVector objA) {
		assertEquals("BhekiMpilo.jpg", objA.getImageName());
	}

	private void testBlueChannelMean(ColorMeanVector objA) {
		assertEquals(20, (int)objA.getRedChannelMean());
	}

	private void testRedChannelMean(ColorMeanVector objA) {
		assertEquals(100, (int)objA.getGreenChannelMean());
	}

	private void testGreenChannelMean(ColorMeanVector objA) {
		assertEquals(200, (int)objA.getBlueChannelMean());
	}

	private void testInputUnitGUIState(InputUnit input) {
	    assertEquals(true, input.getGUIState().equals("1"));
	}

	private void testInputUnitInvalidSortParamChoice() {
		try {
		    InputUnit invalidInput = new InputUnit("tests/", "2", "5", "0");
		    fail("Fail! An exception was expected to be thrown as InputUnit object is fed invalid input.");
		} catch (ImageSortException e) {
			assertEquals("An input error occured: Invalid sorting parameter choice.", e.getMessage());
		}
	}

	private void testInputUnitInvalidSortAlgChoice() {
		try {
		    InputUnit invalidInput = new InputUnit("tests/", "5", "1", "1");
		    fail("Fail! An exception was expected to be thrown as InputUnit object is fed invalid input.");
		} catch (ImageSortException e) {
			assertEquals("An input error occured: Invalid sorting algorithm choice.", e.getMessage());
		}
	}

	private void testInputUnitInvalidGUIStateChoice() {
		try {
		    InputUnit invalidInput = new InputUnit("tests/", "2", "1", "2");
		    fail("Fail! An exception was expected to be thrown as InputUnit object is fed invalid input.");
		} catch (ImageSortException e) {
			assertEquals("An input error occured: Invalid GUI state choice.", e.getMessage());
		}
	}

	private void testImageSortExceptionDefaultConstructor() {
		try {
			throw new ImageSortException();
		} catch (ImageSortException e) {
			assertEquals("An unknown error occured.", e.getMessage());
		}
	}

	private void testImageSortExceptionNonDefaultConstructor() {
		try {
			throw new ImageSortException("This is a test.");
		} catch (ImageSortException e) {
			assertEquals("This is a test.", e.getMessage());
		}
	}

}
