
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GraphDisplay extends JPanel implements MouseMotionListener
{
    GeometricObject[] gArray; //geometric objects
    private BufferedImage image; //background image
    String description; //description of map element
    
    /**
     * Parameterized constructor.
     */
    public GraphDisplay(int frameWidth, int frameHeight, GeometricObject[] g)
    {
        gArray = g;
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
     * Determines which element the mouse button is hovering on and sets 
     * output string accordingly.
     * 
     * @param e contains current location of mouse
     */
    public void mouseMoved(MouseEvent e)
    {        
        //is current mouse location inside a geometric object?
        //Set description accordingly
        Point p = new Point(e.getPoint().x, e.getPoint().y);
        description = "";
               
        for(int i=0; i<gArray.length; ++i){
            Polygon poly = (Polygon) gArray[i];

            if(poly.isPointInPolygon(p)) {
                switch(i) {
                    case 0:
                        description = "Key Biscayne";
                        break;
                    case 1:
                        description = "Virginia Key";
                        break;
                    case 2:
                        description = "FIU MMC Campus";
                        break;
                    case 3:
                        description = "Miami International Airport";
                        break;
                    case 4:
                        description = "Downtown";
                        break;
                    case 5:
                        description = "Coconut Grove";
                        break;
                    case 6:
                        description = "Miami Beach";
                        break;
                }
                break;
            }

        }
        
        repaint();
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
        
        //outputs description
        g.setColor(new Color(64, 64, 64, 128));
        g.fillRoundRect(0, 420, 150, 30, 5, 5); //bottomleft textfield for output
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 12));
        g.drawString(description, 20, 440); //outputs description of map element
    }
}
