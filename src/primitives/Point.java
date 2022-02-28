package primitives;

public class Point {
    final Double3 xyz;

    public Point(Double x, Double y, Double z) {
        xyz= new Double3(x, y, z);
    }
    public  Point(Double3 newD){
        xyz=new Double3(newD.d1, newD.d2, newD.d3);
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

    public double distanceSquared(Point newP){
        Vector newHelp=new Vector(this.xyz.subtract(newP.xyz));
        return (newHelp.xyz.d1*newHelp.xyz.d1)+(newHelp.xyz.d2*newHelp.xyz.d2)+(newHelp.xyz.d3*newHelp.xyz.d3);
    }
    public double distance(Point p){
        return Math.sqrt(distanceSquared(p));
    }
}
