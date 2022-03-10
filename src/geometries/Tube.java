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
        Vector vector= new Vector(p1._xyz);
        double dotProduct=axisRay.getDir().dotProduct(p1.subtract(axisRay.getP0()));
        Point o=axisRay.getP0().add(axisRay.getDir().crossProduct(new Vector(dotProduct,dotProduct,dotProduct)));
        return p1.subtract(o).normalize();
    }
}
