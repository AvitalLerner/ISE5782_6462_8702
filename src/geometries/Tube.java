package geometries;

import geometries.Geometry;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Tube implements Geometry {
    Ray axisRay;
    Double Radius;

    /**
     * constructor get axis and radius.
     * @param axis
     * @param radius
     */
    public Tube(Ray axis, double radius)
    {
        axisRay = axis;
        Radius=radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public Double getRadius() {
        return Radius;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", Radius=" + Radius +
                '}';
    }

    /**
     * calculate the normal
     * @param p1
     * @return now this return NULL
     */
    @Override
    public Vector getNormal(Point p1) {
        Vector vector=axisRay.getDir();
        Point p0=axisRay.getP0();
        Vector p0_p1=p1.subtract(p0);
        double s = alignZero(vector.dotProduct(p0_p1));
        if(isZero(s)){
            return p0_p1.normalize();
        }
        Point o=p0.add(vector.scale(s));
        Vector n=p1.subtract(o).normalize();
        return n;
    }
}