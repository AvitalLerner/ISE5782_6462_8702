package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class circle extend plane and there are center and radius of the circle
 */
public class Circle extends Plane{
    /**
     * the center of the circle
     */
    private final Point _center;
    /**
     * the radius of the circle
     */
    private final double _radius;

    public Circle(Point p1, Vector normal, Point center, double radius) {
        super(p1, normal);
        _center =center;
        _radius=radius;
    }

    /**
     * getter of center
     * @return center
     */
    public Point getCenter() {
        return _center;
    }

    /**
     * getter of radius
     * @return radius
     */
    public double getRadius() {
        return _radius;
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray r, double distance) {
        List<GeoPoint> intersection= super.findGeoIntersectionsHelper(r, distance);
        //check if there is intersection with the plane of the circle
        if(isZero(intersection.size()))
            return null;

        // check if the intersection is in the circle
        if(alignZero(intersection.get(0).point.distance(_center))>=_radius)
            return null;
        else
            return intersection;
    }
}
