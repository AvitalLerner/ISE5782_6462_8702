package primitives;

import geometries.Geometry;

public class Tube implements Geometry {
    Ray axisRay;
    Double Radius;

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

    @Override
    public Vector getNormal(Point p1) {
        return null;
    }
}
