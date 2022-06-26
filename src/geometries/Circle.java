package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Circle extends Plane{
    private Point _center;
    private double _radius;

    public Circle(Point p1, Vector normal, Point center, double radius) {
        super(p1, normal);
        _center =center;
        _radius=radius;
    }

    public Point getCenter() {
        return _center;
    }

    public double getRadius() {
        return _radius;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray r, double distance) {
        List<GeoPoint> intersections= super.findGeoIntersectionsHelper(r, distance);
        if(isZero(intersections.size()))
            return null;

        if(alignZero(intersections.get(0).point.distance(_center))>=_radius)
            return null;
        else
            return intersections;
    }
}
