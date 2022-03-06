package primitives;

public class Point {
    final Double3 xyz;

    /**
     * constructor receives 3 parm
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        xyz= new Double3(x, y, z);
    }

    /**
     * constructor receives Double3 object
     * @param newD
     */
    public  Point(Double3 newD){
        xyz=new Double3(newD.d1, newD.d2, newD.d3);
    }

    /**
     * gets a new point and creates a vector
     * from the point we have to the new one
     * @param other
     * @return the new vector
     */
    public Vector subtract(Point other){
       return new Vector(xyz.subtract(other.xyz));
    }

    /**
     * adds a new vector to the point
     * @param vector
     * @return new point
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }
    /**
     *
     * @param o
     * @return true if the double3 object is the same as the one given
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }

    /**
     * gets a new point and calculates the squared distance
     * @param newP
     * @return distance squared
     */
    public double distanceSquared(Point newP){
        Vector newHelp=new Vector(this.xyz.subtract(newP.xyz));
        return (newHelp.xyz.d1*newHelp.xyz.d1)+(newHelp.xyz.d2*newHelp.xyz.d2)+(newHelp.xyz.d3*newHelp.xyz.d3);
    }

    /**
     * calculates the distance between new point to old
     * @param p
     * @return distance
     */
    public double distance(Point p){
        return Math.sqrt(distanceSquared(p));
    }
}
