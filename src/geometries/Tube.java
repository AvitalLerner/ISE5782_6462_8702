package geometries;

import geometries.Geometry;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry {
    Ray axisRay;
    Double Radius;

    /**
     * constructor get axis and radius.
     * @param axis
     * @param radius
     */
    public Tube(Ray axis, Double radius)
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
        return null;
    }
}
