package primitives;

public class Point {
    final Double3 xyz;

    /**
     * constructor receives 3 parm
     * @param x
     * @param y
     * @param z
     */
    public Point(Double x, Double y, Double z) {
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
        Double3 cordinates=new Double3(
                xyz.d1-other.xyz.d1,
                xyz.d2-other.xyz.d2,
                xyz.d3-other.xyz.d3
        );
        return new Vector(cordinates.d1,cordinates.d2,cordinates.d3);
    }

    /**
     * adds a new vector to the point
     * @param vector
     * @return new point
     */
    public Point add(Vector vector) {
        double x = xyz.d1 + vector.xyz.d1;
        double y = xyz.d2 + vector.xyz.d2;
        double z = xyz.d3 + vector.xyz.d3;
        return new Point(x,y,z);
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
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
