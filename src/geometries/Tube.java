package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class
 */
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
     * @return axis
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     * @return radius
     */
    public Double getRadius() {
        return _radius;
    }

    /**
     * @return string of the data of the tube
     */
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + _axisRay +
                ", Radius=" + _radius +
                '}';
    }

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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance) {
        Vector vAxis = _axisRay.getDir();
        Vector v = ray.getDir();
        Point p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(_axisRay.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0) // the ray is orthogonal to Axis
                return List.of(new GeoPoint(this, ray.getPoint(_radius)));

            double t = alignZero(Math.sqrt(_radius * _radius / vMinusVVaVa.lengthSquared()));
            return t == 0 ? null : List.of(new GeoPoint(this,ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(_radius * _radius / a));
                return t == 0 ? null : List.of(new GeoPoint(this,ray.getPoint(t)));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - _radius * _radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this,ray.getPoint(t2)));
        else // t2 is behind the head
            return List.of(new GeoPoint(this,ray.getPoint(t1)));

    }
}