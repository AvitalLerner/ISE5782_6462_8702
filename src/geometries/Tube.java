package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Tube extends Geometry {
    Ray _axisRay;
    Double _radius;

    /**
     * constructor get axis and radius.
     * @param axis central axis of the tube
     * @param radius the of the tube
     */
    public Tube(Ray axis, double radius)
    {
        _axisRay = axis;
        _radius =radius;
    }

    /**
     *
     * @return axis
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     *
     * @return radius
     */
    public Double getRadius() {
        return _radius;
    }

    /**
     *
     * @return string of the data of the tube
     */
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + _axisRay +
                ", Radius=" + _radius +
                '}';
    }

    /**
     * calculate the normal of p1
     * @param p1 point to calculate the normal
     * @return now this return NULL
     */
    @Override
    public Vector getNormal(Point p1) {
        Vector vector= _axisRay.getDir();
        Point p0= _axisRay.getP0();
        Vector p0_p1=p1.subtract(p0);
        double s = alignZero(vector.dotProduct(p0_p1));
        if(isZero(s)){
            return p0_p1.normalize();
        }
        Point o=p0.add(vector.scale(s));
        Vector n=p1.subtract(o).normalize();
        return n;
    }

    /**
     *
     * @param ray Ray pointing towards the graphic object
     * @return intersection between tube and ray
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray r) {
        return null;
    }
}