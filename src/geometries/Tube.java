package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Tube implements Geometry {
    Ray _axisRay;
    Double _radius;

    /**
     * constructor get axis and radius.
     * @param axis
     * @param radius
     */
    public Tube(Ray axis, double radius)
    {
        _axisRay = axis;
        _radius =radius;
    }

    public Ray getAxisRay() {
        return _axisRay;
    }

    public Double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + _axisRay +
                ", Radius=" + _radius +
                '}';
    }

    /**
     * calculate the normal
     * @param p1
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

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}