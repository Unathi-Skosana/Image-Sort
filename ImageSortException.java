/**
 * The ImageSortException is a program that handles the errors that
 * might occur in ImageSort.
 *
 * @author  Unathi Koketso Skosana
 * @version 1.0
 * @since   2017-27-02
 */
import java.lang.RuntimeException;

public class ImageSortException extends RuntimeException {
    /**
     * Serialization Identifier
     */
    private static final long serialVersionUID = 1L;

    /**
     * constructor instatiates an ImageSortException instance
     *
     * @param errorMessage An error message to shown to the user.
     */
    public ImageSortException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * A constructor with arguments, an instatiates a
     * ImageSortException instance with a default error
     * message
     */
    public ImageSortException() {
        super("An unknown error occured.");
    }
}
