package primitives;

public class Point {
    final Double3 _xyz;

    /**
     * primary constructor receives 3 parameters
     *
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        _xyz = new Double3(x, y, z);
    }

    /**
     * constructor receives Double3 object
     *
     * @param xyz
     */
    public Point(Double3 xyz) {
        _xyz = xyz;
    }

    /**
     * gets a new point and creates a vector
     * from the point we have to the new one
     *
     * @param other
     * @return the new vector
     */
    public Vector subtract(Point other) {
        return new Vector(_xyz.subtract(other._xyz));
    }

    /**
     * adds a new vector to the point
     *
     * @param vector
     * @return new point
     */
    public Point add(Vector vector) {
        return new Point(_xyz.add(vector._xyz));
    }

    @Override
    public String toString() {
        return "Point: " + _xyz;
    }

    /**
     * @param o
     * @return true if the double3 object is the same as the one given
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return _xyz.equals(point._xyz);
    }

    /**
     * gets a new point and calculates the squared distance
     *
     * @param newP
     * @return distance squared
     */
    public double distanceSquared(Point newP) {
        Double3 temp = _xyz.subtract(newP._xyz);

        double xx = temp._d1 * temp._d1;
        double yy = temp._d2 * temp._d2;
        double zz = temp._d3 * temp._d3;

        return (xx + yy + zz);
    }

    /**
     * calculates the distance between new point to old
     *
     * @param p
     * @return distance
     */
    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }
}
