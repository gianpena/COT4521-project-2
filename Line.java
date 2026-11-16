import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * This class represents a line in the plane, given by the equation
 * ax + by + c = 0.
 *
 * Based on code from the COT4521 course materials.
 *
 * @author Gian Peña (PID 6396658)
 * @author Henrique Pitta Laranjinha (PID 6527270)
 */
public class Line extends GeometricObject
{

    private double a;   //x coefficient
    private double b;   //y coefficient
    private double c;   //constant term

    /**
     * Instantiates a line object as -x + y = 0 or y = x.
     */
    public Line()
    {
        a = -1;
        b = 1;
        c = 0;
    }

    /**
     * Instantiates a line object as ax + by + c = 0 or y = (-a/b) x + (-c/b).
     */
    public Line(double a, double b, double c) throws IllegalArgumentException
    {
        //a=0 and b=0 does not define a line
        if (a==0 && b==0) throw new IllegalArgumentException();
        else
        {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    /**
     * Instantiates a line object that passes through two given points.
     *
     * @param p one of the points the line passes through
     * @param q one of the points the line passes through
     * @throws IllegalArgumentException if p and q are the same point
     */
    public Line(Point p, Point q) throws IllegalArgumentException
    {
        if (p.getX() == q.getX() && p.getY() == q.getY())
            throw new IllegalArgumentException();

        a = q.getY() - p.getY();
        b = p.getX() - q.getX();
        c = -(a * p.getX() + b * p.getY());
    }
            
    /**
     * Draws this line.
     * 
     * @param g graphic context 
     */
    @Override
    public void draw(Graphics g)
    {
        //finds the window size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double maxX = screenSize.width;
        double maxY = screenSize.height;

        //obtain two points on this line, (x1, y1) and (x2, y2)
        double x1, y1, x2, y2;
        
        if (!isVertical()) //line is not vertical
        {
            x1 = 0.0;
            y1 = getY(x1);
            x2 = maxX;
            y2 = getY(x2);
        }
        else //line is vertical
        {
            y1 = 0.0;
            x1 = getX(y1);
            y2 = maxY;
            x2 = getX(y2);
        }

        g.setColor(getBoundaryColor());
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }    
    
    /**
     * Returns the coefficient of the X variable in the equation of this line
     * object.
     * 
     * @return coefficient of the X variable in the equation of this line object
     */
    public double getA()
    {
        return a;
    }

    /**
     * Returns the coefficient of the Y variable in the equation of this line
     * object.
     * 
     * @return coefficient of the Y variable in the equation of this line object
     */
    public double getB()
    {
        return b;
    }

    /**
     * Returns the independent term of the equation of this line object.
     * 
     * @return independent term of the equation of this line object
     */
    public double getC()
    {
        return c;
    }

    /**
     * Given x, calculates the value of y.
     *
     * @param x x value
     * @return value of y
     * @throws IllegalStateException 
     */
    public double getY(double x) throws IllegalStateException
    {
        if (b != 0)
            return (-a / b) * x + (-c / b);
        else
            throw new IllegalStateException("Vertical line cannot be used here.");
    }

    /**
     * Given y, calculates the value of x.
     *
     * @param y y value
     * @return value of x
     * @throws IllegalStateException 
     */
    public double getX(double y) throws IllegalStateException
    {
        if (a != 0)
            return (-b / a) * y + (-c / a);
        else throw new IllegalStateException("Horizontal line cannot be used here.");
    }
    
    /**
     * Determines if this line is vertical.
     * 
     * @return true if this line is vertical, false otherwise
     */
    public boolean isVertical()
    {
        return b == 0;
    }

    /**
     * Determines if this line is horizontal.
     *
     * @return true if this line is horizontal, false otherwise
     */
    public boolean isHorizontal()
    {
        return a == 0;
    }

    /**
     * Calculates distance from point p to this line.
     *
     * @param p point
     * @return distance from point p to this line
     */
    public double distanceFromPoint(Point p)
    {
        return Math.abs(a * p.getX() + b * p.getY() + c) / Math.sqrt(a * a + b * b);
    }

    /**
     * Returns -1, 0, or 1, depending on the relative position of the given point
     * and this line.
     *
     * Line is not vertical:
     *  -1: if point is below the line
     *   0: if point is on the line
     *   1: if point is above the line
     *
     * Line is vertical:
     *  -1: if point is to the left of the line
     *   0: if point is on the line
     *   1: if point is to the right of the line.
     *
     * @param p point
     * @return -1, 0, or 1
     */
    public int pointRelativePosition(Point p)
    {
        if (!isVertical())
        {
            double lineY = getY(p.getX());
            if (p.getY() < lineY) return -1;
            if (p.getY() > lineY) return 1;
            return 0;
        }
        else
        {
            double lineX = getX(p.getY());
            if (p.getX() < lineX) return -1;
            if (p.getX() > lineX) return 1;
            return 0;
        }
    }

    /**
     * Translates this line by given vector.
     *
     * @param v given vector
     */
    public void translate(Vector v)
    {
        c = c - a * v.getX() - b * v.getY();
    }

    /**
     * Finds normal vector (i.e. vector that is perpendicular to this line).
     *
     * @return normal vector
     */
    public Vector normalVector()
    {
        return new Vector(a, b);
    }

    /**
     * Computes intersection point of this line and the given line.
     *
     * @param line given line
     * @return intersection point, or null if lines are parallel or coincident
     */
    public Point intersection(Line line)
    {
        double a1 = this.a, b1 = this.b, c1 = this.c;
        double a2 = line.a, b2 = line.b, c2 = line.c;

        double denom = a1 * b2 - a2 * b1;
        if (denom == 0)
            return null; //parallel or coincident

        double x = (b1 * c2 - c1 * b2) / denom;
        double y = (c1 * a2 - a1 * c2) / denom;

        return new Point(x, y);
    }
        
    /**
     * Sets the coefficient of the X variable in the equation of this line
     * object.
     * 
     * @param a value of the coefficient
     * 
     * @throws IllegalStateException if a=0 and b=0
     */
    public void setA(double a) throws IllegalStateException
    {
        //a=0 and b=0 does not define a line
        if (a==0 && this.b==0) throw new IllegalStateException();
        else
        {
            this.a = a;
        }
    }

    /**
     * Sets the coefficient of the Y variable in the equation of this line
     * object
     * 
     * @param b value of the coefficient
     * 
     * @throws IllegalStateException if a=0 and b=0
     */
    public void setB(double b) throws IllegalStateException
    {
        //a=0 and b=0 does not define a line
        if (this.a==0 && b==0) throw new IllegalStateException();
        else
        {
            this.b = b;
        }
    }

    /**
     * Sets the independent term of the equation of this line object.
     * 
     * @param c independent term
     */
    public void setC(double c)
    {
        this.c = c;
    }

    /**
     * Sets this line object as ax + by + c = 0 or y = (-a/b) x + (-c/b).
     */
    public void setLine(double a, double b, double c) throws IllegalStateException
    {
        //a=0 and b=0 does not define a line
        if (a==0 && b==0) throw new IllegalStateException();
        else
        {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
    
    /**
     * Constructs a String description of this line.
     * 
     * @return String containing a description of this line
     */
    @Override
    public String toString()
    {
        String str = "LINE " + a + " " + b + " " + c + " " + super.toString();

        return str;
    }    
}
