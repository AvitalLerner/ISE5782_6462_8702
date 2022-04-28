package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

public class Sphere extends Geometry {
    Point _center;
    double _radius;

    /**
     * constructor
     * @param center- the center of the sphere
     * @param radius- the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        _center = center;
        _radius = radius;
    }

    /**
     * @return string of the data of the sphere
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }

    /**
     * @return center
     */
    public Point getCenter() {
        return _center;
    }

    /**
     * @return radius
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * @param p1 point to calculate the normal
     * @return normal
     */
    @Override
    public Vector getNormal(Point p1) {

      return p1.subtract(_center).normalize()  ;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray r) {
        return null;
    }
}