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
     *
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
     * function get
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

        return p1.subtract(_center).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray r, double distance) {
        Point p0 = r.getP0();
        Vector v = r.getDir();
        Vector u;

        if (_center.equals(p0)) {
            return List.of(new GeoPoint(this, (r.getPoint(this._radius))));
        }

            u = _center.subtract(p0);   // p0 == _center
        double tm = alignZero(v.dotProduct(u));
        double d = Math.sqrt(u.lengthSquared() - tm * tm);
        double thSqrt = alignZero(this._radius * this._radius - d * d);

        double th = alignZero(Math.sqrt(thSqrt));
        if (th == 0) return null;

        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);

        if (t1 <= 0 && t2 <= 0) {
            return null;
        }

        if (alignZero(distance - t1) > 0 && alignZero(distance - t2) > 0) {
            return List.of(
                    new GeoPoint(this, (r.getPoint(t1))),
                    new GeoPoint(this, (r.getPoint(t2)))
            );
        }

        if (t1 > 0 && alignZero(t1 - distance) < 0) {
            return List.of(
                    new GeoPoint(this, (r.getPoint(t1))));
        }

        if (t2 > 0 && alignZero(t2 - distance) < 0) {
            return List.of(
                    new GeoPoint(this, (r.getPoint(t2))));
        }

        return null;

    }
}