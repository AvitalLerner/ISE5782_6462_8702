package primitives;

public class Point {
    final Double3 xyz;

    public Point(Double x, Double y, Double z) {
        xyz= new Double3(x, y, z);
    }
    public Vector subtract(Point other){
        Double3 cordinates=new Double3(
                xyz.d1-other.xyz.d1,
                xyz.d2-other.xyz.d2,
                xyz.d3-other.xyz.d3
        );
        return new Vector(cordinates.d1,cordinates.d2,cordinates.d3);
    }
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
}
