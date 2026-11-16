
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class represents the panel where the map is drawn. It listens for
 * mouse movement and displays a description of the map element under the
 * mouse.
 *
 * Based on code from the COT4521 course materials.
 *
 * @author Gian Peña (PID 6396658)
 * @author Henrique Pitta Laranjinha (PID 6527270)
 */
public class GraphDisplay extends JPanel implements MouseMotionListener
{
    private Polygon[] polArray;     //map elements
    private String[] descriptions;  //description of each map element
    private BufferedImage image;    //background image
    private String description;     //description currently displayed

    /**
     * Parameterized constructor.
     *
     * @param frameWidth width of the panel
     * @param frameHeight height of the panel
     * @param g map elements; every element must be a Polygon
     * @param desc descriptions of the map elements, in the same order as g
     */
    public GraphDisplay(int frameWidth, int frameHeight, GeometricObject[] g, String[] desc)
    {
        polArray = new Polygon[g.length];
        for (int i = 0; i < g.length; i++)
        {
            polArray[i] = (Polygon) g[i];
        }
        descriptions = desc;
        description = "";

        Dimension d = new Dimension(frameWidth, frameHeight);
        setPreferredSize(d); //sets the preferred size of this panel
        addMouseMotionListener(this);

        //opens/read image file
        try
        {
            image = ImageIO.read(new File("./miami.png"));
        }
        catch (IOException ex)
        {
            System.out.println("Image file not found!");
        }
    }

    public void mouseDragged(MouseEvent e){}

    /**
     * Determines which element the mouse is hovering on and sets
     * output string accordingly.
     *
     * @param e contains current location of mouse
     */
    public void mouseMoved(MouseEvent e)
    {
        Point p = new Point(e.getX(), e.getY());
        int index = Algorithms.isPointInSet(polArray, p);

        String newDescription = (index == -1) ? "" : descriptions[index];
        if (!newDescription.equals(description))
        {
            description = newDescription;
            repaint();
        }
    }

    /**
     * Paints this panel; the system invokes it every time
     * it deems necessary to redraw the panel.
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g); //clears window

        //draws background image
        Dimension d = getSize();
        g.drawImage(image, 0, 0, d.width, d.height, this);

        //outputs description in the bottom-left text area
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontMetrics fm = g.getFontMetrics();
        int boxWidth = Math.max(200, fm.stringWidth(description) + 30);
        int boxHeight = 30;
        int boxY = d.height - boxHeight - 10;

        g.setColor(new Color(64, 64, 64, 160));
        g.fillRoundRect(10, boxY, boxWidth, boxHeight, 5, 5);
        g.setColor(Color.WHITE);
        g.drawString(description, 25, boxY + 20);
    }
}
