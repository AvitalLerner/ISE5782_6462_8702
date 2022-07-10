package primitives;

/**
 * class Vector extends Point.
 * the vector have 3 coordinates to describe direction
 */
public class Vector extends Point {
    /**
     * constructor receives 3 double values
     * @param d1 first parameter of the vector
     * @param d2 second parameter of the vector
     * @param d3 third parameter of the vector
     */
    public Vector(double d1, double d2, double d3) {
        super(d1,d2,d3);
        if(Double3.ZERO.equals(new Double3(d1,d2,d3))){
            throw new IllegalArgumentException("ZERO vector is not allowed");
        }

    }

    /**
     * constructor receives Double3 object
     * @param newD is a point for describing the vector
     */
    public Vector(Double3 newD){
        super(newD);
        if(Double3.ZERO.equals(new Double3(newD.d1, newD.d2, newD.d3))){
            throw new IllegalArgumentException("ZERO vector is not allowed");
        }
    }

    /**
     * checks if two vectors are equals
     * @param o object to check if is equal to this
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Vector.super.equals(vector);
    }
    /**
     * calculates the length of the squared vector
     * @return squared vector length
     */
    public double lengthSquared() {
        Double3 newp= _xyz.product(_xyz);
        return newp.d1 +newp.d2 + newp.d3;
    }

    /**
     * calculates vector length
     * @return vector length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     *calculates the dot product between new vector and old
     * @param vector to calculate the dot product
     * @return scalar
     */
    public double dotProduct(Vector vector) {
        double u1 = _xyz.d1;
        double u2 = _xyz.d2;
        double u3 = _xyz.d3;

        double v1 = vector._xyz.d1;
        double v2 = vector._xyz.d2;
        double v3 = vector._xyz.d3;

        return u1*v1 +u2*v2 +u3*v3;

    }

    /**
     * calculates cross product of new vector and old
     * @param v vector to calculate the cross product
     * @return cross product new vector
     */
    public Vector crossProduct(Vector v) {
        double u1 = _xyz.d1;
        double u2 = _xyz.d2;
        double u3 = _xyz.d3;

        double v1 = v._xyz.d1;
        double v2 = v._xyz.d2;
        double v3 = v._xyz.d3;

        return new Vector(u2 * v3 - u3 * v2,u3*v1-u1*v3,u1*v2-u2*v1);
    }

    /**
     * multiplies the vector by a scalar
     * @param scaleFactor number to multiply the vector by it
     * @return new vector oldVector*scalar
     */
    public Vector scale(double scaleFactor){
        if(scaleFactor==0)
        {
            throw  new IllegalArgumentException("scale resulting by 0 not valid ");

        }
        Double3 coordinate = new Double3(_xyz.d1 *scaleFactor, _xyz.d2 *scaleFactor, _xyz.d3 *scaleFactor);
        return new Vector(coordinate.d1, coordinate.d2,coordinate.d3);
    }

    /**
     * adds two vectors old and new
     * @param vector to add for this vector
     * @return new vector old+new
     */
    public Vector add(Vector vector)
    {
        Double3 coordinate = new Double3( _xyz.d1 + vector._xyz.d1, _xyz.d2 + vector._xyz.d2, _xyz.d3 + vector._xyz.d3);
        if(Double3.ZERO.equals(coordinate)){
            throw new IllegalArgumentException("add resulting by 0 not valid");
        }
        return new Vector(coordinate.d1, coordinate.d2,coordinate.d3);
    }

    /**
     * calculates vectors normal
     * @return normal
     */
    public Vector normalize() {
        double len=length();
        return new Vector(_xyz.reduce(len));
    }
}