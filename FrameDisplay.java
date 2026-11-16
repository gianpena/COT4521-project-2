
import javax.swing.*;

/**
 * This class represents the application window that holds the map panel.
 *
 * Based on code from the COT4521 course materials.
 *
 * @author Gian Peña (PID 6396658)
 * @author Henrique Pitta Laranjinha (PID 6527270)
 */
public class FrameDisplay extends JFrame
{
    int WIDTH = 1000;
    int HEIGHT = 560;

    private GraphDisplay panel;

    /**
     * Creates an initializes the window frame.
     *
     * @param g geometric objects to be depicted in this frame
     * @param desc descriptions of the geometric objects, in the same order as g
     */
    public FrameDisplay(GeometricObject[] g, String[] desc)
    {
        setTitle("Interactive Map of Miami");
        setSize(WIDTH, HEIGHT);
        setResizable(false);

        panel = new GraphDisplay(WIDTH, HEIGHT, g, desc);
        add(panel);

        pack(); //sizes the frame so that the panel is at, or above,
                //the preferred size
    }
}
