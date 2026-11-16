import java.awt.Graphics;
import java.util.Arrays;

/**
 * This class represents a simple polygon; polygon is given by its vertices 
 * listed in counterclockwise order.
 *
 * Based on code from the COT4521 course materials.
 *
 * @author Gian Peña (PID 6396658)
 * @author Henrique Pitta Laranjinha (PID 6527270)
 */
public class Polygon extends GeometricObject
{

    private Point[] vertexList; //list of vertices in counterclockwise order
    private int size;   //capacity of array vertexList
    private int vertNumber; //number of vertices of this polygon

    /**
     * Instantiates a Polygon object. The polygon vertices are given in an array
     * of Point type. All points in the array will become vertices of the polygon.
     * 
     * @param pointList array of points that will form the polygon vertices;
     *                  must have at least 3 vertices.
     */
    public Polygon(Point[] pointList) throws IllegalArgumentException
    {
        //less that 3 vertices do not define a polygon
        if (pointList.length < 3) throw new IllegalArgumentException(
                      "Attempt to define a polygon with less than 3 vertices");
        else
        {
            vertNumber = size = pointList.length;
            vertexList =  new Point[size];

            for (int i=0; i<vertNumber; i++)
            {
                vertexList[i] = pointList[i];
            }
        }
    }
    
    /**
     * Adds a vertex as the last one in the sequence of vertices. Vertex array
     * will be expanded to double its size if vertex cannot be added because
     * array is full.
     *
     * @param v the new vertex
     */
    public void addVertex(Point v)
    {
        if (vertNumber == size)
        {
            size = 2*size;
            vertexList = Arrays.copyOf(vertexList, size);
        }
        
        vertexList[vertNumber] = v;
        vertNumber++;
    }
   
    /**
     * Draws this polygon.
     * 
     * @param g graphics context 
     */
    @Override
    public void draw(Graphics g)
    {
        int[] x = new int[vertNumber];
        int[] y = new int[vertNumber];
        
        for(int i=0; i<vertNumber; i++)
        {
            x[i] = (int)vertexList[i].getX();
            y[i] = (int)vertexList[i].getY();
        }
        
        g.setColor(getInteriorColor());
        g.fillPolygon(x, y, vertNumber);
        g.setColor(getBoundaryColor());
        g.drawPolygon(x, y, vertNumber);
    }

    /**
     * Computes the area of this polygon.
     *
     * @return area
     */
    public double area()
    {
        double sum1 = 0.0, sum2 = 0.0;
        Point v1, v2;
        for (int i = 0; i < vertNumber; i++)
        {
            v1 = vertexList[i];
            v2 = vertexList[(i + 1) % vertNumber];
            sum1 += v1.getX() * v2.getY();
            sum2 += v2.getX() * v1.getY();
        }
        // Shoelace Formula to calculate the area
        return 0.5 * Math.abs(sum1 - sum2);
    }
    
    /**
     * Obtains the edges of the boundary of this polygon.
     * 
     * @return array of line segments in the boundary of this polygon, counterclockwise 
     */
    public LineSegment[] getEdges()
    {
        LineSegment[] edges = new LineSegment[vertNumber];
        for(int i=0; i<vertNumber; i++)
        {
            double x1 = vertexList[i].getX();
            double y1 = vertexList[i].getY();
            Point v1 = new Point(x1, y1);
            
            double x2 = vertexList[(i+1)%vertNumber].getX();
            double y2 = vertexList[(i+1)%vertNumber].getY();
            Point v2 = new Point(x2, y2);
            
            edges[i] = new LineSegment(v1, v2);
        }
        
        return edges;
    }

    /**
     * Returns the number of vertices in this polygon
     *
     * @return number of vertices in this polygon
     */
    public int getNumberOfVertices()
    {
        return vertNumber;    
    }

    /**
     * Obtains the vertices of the boundary of this polygon.
     * 
     * @return array of vertices in the boundary of this polygon, counterclockwise
     */
    public Point[] getVertices()
    {
        Point[] vertices = new Point[vertNumber];
        for(int i=0; i<vertNumber; i++)
        {
            vertices[i] = vertexList[i];
        }
        
        return vertices;
    }

    /**
     * Computes the point in this polygon's boundary that is closest to a given point p.
     *
     * @param p given point
     * @return closest point in this polygon's boundary
     */
    public Point closestPoint(Point p)
    {
        LineSegment[] edges = getEdges();
        return p.closestPoint(edges);
    }
        
    /**
     * Determines the greatest x.
     *
     * @return the greatest x
     */
    public double greatestX()
    {
        double maxX = vertexList[0].getX();
        for (int i = 1; i < vertNumber; i++)
        {
            double xVal = vertexList[i].getX();

            if (xVal > maxX)
                maxX = xVal;
        }

        return maxX;
    }

    /**
     * Determines the greatest y.
     *
     * @return the greatest y
     */
    public double greatestY()
    {
        double maxY = vertexList[0].getY();
        for (int i = 1; i < vertNumber; i++)
        {
            double yVal = vertexList[i].getY();

            if (yVal > maxY)
                maxY = yVal;
        }

        return maxY;
    }
    
    /**
     * Determines the smallest x.
     *
     * @return the smallest x
     */
    public double smallestX()
    {
        double minX = vertexList[0].getX();
        for (int i = 1; i < vertNumber; i++)
        {
            double xVal = vertexList[i].getX();

            if (xVal < minX)
                minX = xVal;
        }

        return minX;
    }

    /**
     * Determines the smallest y.
     *
     * @return the smallest y
     */
    public double smallestY()
    {
        double minY = vertexList[0].getY();
        for (int i = 1; i < vertNumber; i++)
        {
            double yVal = vertexList[i].getY();

            if (yVal < minY)
                minY = yVal;
        }

        return minY;
    }

    /**
     * Determines if a point is in the interior of this polygon using the ray-casting algorithm.
     *
     * @param p given point
     * @return true if p is in the interior of this polygon; false otherwise
     */
    public boolean isPointInPolygon(Point p)
    {
        //check if point is on boundary
        LineSegment[] edges = getEdges();
        for (LineSegment e : edges)
        {
            Point c = p.closestPoint(e);
            if (p.distance(c) == 0)
                return true;
        }

        int i = 0;
        double px = p.getX();
        double py = p.getY();

        for (LineSegment e : edges)
        {
            Point l = e.getBegin().getY() < e.getEnd().getY() ? e.getBegin() : e.getEnd();
            Point u = e.getBegin().getY() < e.getEnd().getY() ? e.getEnd() : e.getBegin();

            //skip horizontal edges
            if (l.getY() == u.getY()) continue;

            double ly = l.getY();
            double uy = u.getY();
            double lx = l.getX();
            double ux = u.getX();

            //lower end closed, upper end open
            if (ly <= py && py < uy)
            {
                //compute x coordinate of edge at py
                double x = lx + (py - ly) * (ux - lx) / (uy - ly);
                if (px < x)
                    i++;
            }
        }

        return i % 2 == 1;
    }
    
    /**
     * Constructs a String description of this polygon.
     *
     * @return String containing description of this polygon.
     */
    @Override
    public String toString()
    {
        String str = "POLYGON " + vertNumber + " " + super.toString() + "\n";

        for (int i = 0; i<vertNumber-1; i++)
        {
            str += vertexList[i] + "\n";
        }
        
        str += vertexList[vertNumber-1];

        return str;
    }  

    /**
     * Rotates this simple polygon about the origin by the given angle.
     *
     * @param angle angle in degrees
     */
    public void rotate(double angle)
    {
        for (int i=0; i<vertNumber; i++)
        {
            vertexList[i].rotate(angle);
        }
    }
    
    /**
     * Translates this simple polygon by given vector.
     *
     * @param v given vector
     */
    public void translate(Vector v)
    {
        for (int i=0; i<vertNumber; i++)
        {
            vertexList[i].translate(v);
        }
    }

    public boolean intersect(Polygon pol) {
        for(LineSegment i : getEdges())
            for(LineSegment j : pol.getEdges())
                if(i.intersect(j))
                    return true;
        
        for(Point p : getVertices())
            if(pol.isPointInPolygon(p))
                return true;
        
        for(Point p : pol.getVertices())
            if(isPointInPolygon(p))
                return true;
            
        return false;
    }
}
