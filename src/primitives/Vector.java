package primitives;

public class Vector extends Point {

    public Vector(double d1, double d2, double d3) {
        super(d1,d2,d3);
        if(isZero(d1)&&isZero(d2)&&isZero(d3)){
            throw new IllegalArgumentException("ZERO vector is not allowed");
        }

    }

    public double lengthSquared() {
        return xyz.d1* xyz.d1 + xyz.d2* xyz.d2 + xyz.d3* xyz.d3;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     *
     *
     * @param vector
     * @return
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

    public Vector crossProduct(Vector v) {
        double u1 = xyz.d1;
        double u2 = xyz.d2;
        double u3 = xyz.d3;

        double v1 = v.xyz.d1;
        double v2 = v.xyz.d2;
        double v3 = v.xyz.d3;

        return new Vector(
                (u2 * v3 - u3 * v2, u1 * v3 - u3 * v1,                u1*v2-u2*v1)
                );
    }

    public Vector scale(double scaleFactor){
        if(isZero(scaleFactor))
        {
            throw  new IllegalArgumentException("scale resulting by 0 not valid ")

        }
        return new Vector()
        Double3 coordinate = new Double3(xyz.d1/scaleFactor, xyz.d2/scaleFactor,xyz.d3/scaleFactor);
    }
    public Vector add(Vector vector)
    {
        Double3 coordinate = new Double3( xyz.d1 + vector.xyz.d1, xyz.d2 + vector.xyz.d2, xyz.d3 + vector.xyz.d3);
        if(Double3.ZERO.equals(coordinate)){
            throw new
        }
    }

    public Vector normalize() {
        double len=length();
        return this.scale(1d/len);

    }
}
