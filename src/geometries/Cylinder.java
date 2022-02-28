package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube {
    double height;
    public Cylinder(double h, Ray r){
        super(r,h);
      this.height=h;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                '}';
    }

    @Override
    public Vector getNormal(Point p1) {
        return null;
    }
}
