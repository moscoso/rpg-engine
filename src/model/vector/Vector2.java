package model.vector;

/**
 * A vector is an object that has both a magnitude and a direction.
 * Geometrically, we can picture a vector as a directed line segment, 
 * whose length is the magnitude of the vector and with an arrow indicating the direction
 */
public class Vector2 {
    public double x;
    public double y;
       
    /**
     * @summary
     * The default constructor creates a Zero Vector, a vector of length 0, and thus has all components equal to 0.
     * @note
     * If all the components of →x are Zero, it is called the Zero vector. 
     * If the length of a vector →x is Zero then, it is called the null vector. 
     * In n dimensional Euclidean space (En), there is no distinction between zero vector and null vector
     */
    public Vector2() {
        this.x = 0.0;
        this.y = 0.0;
    }
       
    /**
     * Creates a 2D Vector with the specified components
     * @param x the x component of a 2D Vector
     * @param y the y component of a 2D Vector
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
       
    /**
     * Checks for equality between this 2D Vector and another 2D Vector
     * @param other another 2D Vector to compare to
     * @return true if both vectors have the same components
     */
    public boolean isEqualTo(Vector2 other) {
        return (this.x == other.x && this.y == other.y);
    }

    /**
     * In mathematics, the Euclidean distance between two points in Euclidean space 
     * is the length of a line segment between the two points. 
     * It can be calculated from the Cartesian coordinates of the points 
     * using the Pythagorean theorem, therefore occasionally being called the Pythagorean distance. 
     * @param a a 2D Vector
     * @param b another 2D Vector
     * @return the distance between two 2D Vectors
     */
    public static double distance(Vector2 a, Vector2 b) {
        double v0 = b.x - a.x;
        double v1 = b.y - a.y;
        return Math.sqrt(v0*v0 + v1*v1);
    }

    /**
     * To normalize a vector means to change it so that it points in the same direction 
     * (think of that line from the origin) but its length is 1
     * @return a 2D Vector of length 1 that has the same direction as this Vector
     */
    public Vector2 normalize() {
        double length = this.magnitude();
        if (length == 0) {
            return new Vector2();
        } else {
            return new Vector2(x/length, y/length);
        }
    }

    /**
     * The magnitude of a vector is the length of the vector. 
     * The magnitude of the vector a is denoted as ∥a∥.
     * @return the length of the vector
     */
    public double magnitude() {
        return Math.sqrt(x + y);
    }
}
