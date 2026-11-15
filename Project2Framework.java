
import javax.swing.*;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Project 2 code skeleton; shows how to add a background image.
 * 
 * @author Antonio Hernandez
 */
public class Project2Framework
{
    public static void main(String[] args)
    throws FileNotFoundException {
        new Project2Framework();
    }
    
    public Project2Framework()
    throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("miami_neighborhoods_polygons.txt"));
        int n = in.nextInt();

        //Defining the geometric objects that represent the problem data
        GeometricObject[] g = new GeometricObject[n];
        for(int i=0; i<n; ++i){
            int m = in.nextInt();
            Point[] points = new Point[m];

            for(int j=0; j<m; ++j) {
                int x = in.nextInt(), y = in.nextInt();
                points[j] = new Point(x, y);
            }
            g[i] = new Polygon(points);
        }
        in.close();
        
        ////////////////////////////////////////////////////////////////////////
        //populate g with geometric objects
        ////////////////////////////////////////////////////////////////////////        
        
        //graphing
        FrameDisplay frame = new FrameDisplay(g);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
