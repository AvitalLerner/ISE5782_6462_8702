package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry {
    Point center;
    double radius;

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point p1) {
        return null;
    }
}
