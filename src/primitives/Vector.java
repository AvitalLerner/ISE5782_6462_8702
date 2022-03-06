package primitives;

public class Vector extends Point {
    /**
     * constructor receives 3 double values
     * @param d1
     * @param d2
     * @param d3
     */
    public Vector(double d1, double d2, double d3) {
        super(d1,d2,d3);
        if(Double3.ZERO.equals(new Double3(d1,d2,d3))){
            throw new IllegalArgumentException("ZERO vector is not allowed");
        }

    }

    /**
     * constructor receives Double3 object
     * @param newD
     */
    public Vector(Double3 newD){
        super(newD);
        if(Double3.ZERO.equals(new Double3(newD.d1, newD.d2, newD.d3))){
            throw new IllegalArgumentException("ZERO vector is not allowed");
        }
    }

    /**
     * calculates the length of the squared vector
     * @return squared vector length
     */
    public double lengthSquared() {
        Double3 newp=xyz.product(xyz);
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
     *
     *calculates the dot product between new vector and old
     * @param vector
     * @return scalar
     */
    public double dotProduct(Vector vector) {
        double u1 = xyz.d1;
        double u2 = xyz.d2;
        double u3 = xyz.d3;

        double v1 = vector.xyz.d1;
        double v2 = vector.xyz.d2;
        double v3 = vector.xyz.d3;

        return u1*v1 +u2*v2 +u3*v3;

    }

    /**
     * calculates cross product of new vector and old
     * @param v
     * @return cross product new vector
     */
    public Vector crossProduct(Vector v) {
        double u1 = xyz.d1;
        double u2 = xyz.d2;
        double u3 = xyz.d3;

        double v1 = v.xyz.d1;
        double v2 = v.xyz.d2;
        double v3 = v.xyz.d3;

        return new Vector(u2 * v3 - u3 * v2,u3*v1-u1*v3,u1*v2-u2*v1);
    }

    /**
     * multiplies the vector by a scalar
     * @param scaleFactor
     * @return new vector oldvector*scalar
     */
    public Vector scale(double scaleFactor){
        if(scaleFactor==0)
        {
            throw  new IllegalArgumentException("scale resulting by 0 not valid ");

        }
        Double3 coordinate = new Double3(xyz.d1/scaleFactor, xyz.d2/scaleFactor,xyz.d3/scaleFactor);
        return new Vector(coordinate.d1, coordinate.d2,coordinate.d3);
    }

    /**
     * adds two vectors old and new
     * @param vector
     * @return new vector old+new
     */
    public Vector add(Vector vector)
    {
        Double3 coordinate = new Double3( xyz.d1 + vector.xyz.d1, xyz.d2 + vector.xyz.d2, xyz.d3 + vector.xyz.d3);
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
        return new Vector(xyz.reduce(len));
    }
}
