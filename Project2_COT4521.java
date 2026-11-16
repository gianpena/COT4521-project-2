
import javax.swing.*;

/**
 * Project 2: point location on a map of Miami. Each map element is a simple
 * polygon; hovering the mouse over an element shows its description.
 *
 * Based on the Project 2 code skeleton by Antonio Hernandez (COT4521).
 *
 * @author Gian Peña (PID 6396658)
 * @author Henrique Pitta Laranjinha (PID 6527270)
 */
public class Project2_COT4521
{
    //vertices of each map element in panel coordinates (x1, y1, x2, y2, ...)
    private static final int[][] POLYGONS = {
        //Key Biscayne
        {788, 316, 743, 382, 778, 537, 848, 556, 895, 542, 902, 307, 861, 290},
        //Virginia Key
        {811, 301, 865, 286, 906, 265, 913, 223, 901, 209, 886, 189, 872, 183,
         844, 205, 827, 263, 808, 281, 814, 302},
        //FIU MMC Campus
        {65, 232, 122, 235, 120, 179, 62, 179},
        //Miami International Airport
        {292, 89, 448, 83, 479, 53, 476, 0, 332, 0, 330, 31, 332, 63, 299, 73},
        //Downtown
        {697, 103, 701, 188, 716, 202, 741, 189, 745, 147, 756, 138, 754, 97},
        //Coconut Grove
        {534, 374, 537, 360, 562, 331, 674, 258, 695, 230, 688, 219, 659, 224,
         525, 280, 490, 310, 490, 374},
        //Miami Beach
        {920, 158, 947, 168, 980, 0, 878, 0, 889, 57, 888, 82, 897, 125, 909, 157}
    };

    //description of each map element, in the same order as POLYGONS
    private static final String[] DESCRIPTIONS = {
        "Key Biscayne: island village with Crandon Park and Bill Baggs Cape Florida State Park",
        "Virginia Key: barrier island with Virginia Key Beach Park and Miami Marine Stadium",
        "FIU MMC Campus: Florida International University's main campus",
        "Miami International Airport: the main airport serving Miami-Dade County",
        "Downtown: Miami's central business district on Biscayne Bay",
        "Coconut Grove: one of Miami's oldest neighborhoods, on Biscayne Bay",
        "Miami Beach: barrier island city across Biscayne Bay, home to South Beach"
    };

    public static void main(String[] args)
    {
        new Project2_COT4521();
    }

    public Project2_COT4521()
    {
        //Defining the geometric objects that represent the problem data
        GeometricObject[] g = new GeometricObject[POLYGONS.length];
        for (int i = 0; i < POLYGONS.length; i++)
        {
            int[] coords = POLYGONS[i];
            Point[] points = new Point[coords.length / 2];
            for (int j = 0; j < points.length; j++)
            {
                points[j] = new Point(coords[2 * j], coords[2 * j + 1]);
            }
            g[i] = new Polygon(points);
        }

        //graphing
        FrameDisplay frame = new FrameDisplay(g, DESCRIPTIONS);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
