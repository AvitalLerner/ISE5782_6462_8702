package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class for a 3D plane
 */
public class Plane extends Geometry {
    private final Point _q0;
    private final Vector _normal;

    /**
     * constructor get
     * 3 parameters
     * @param p1 first point in the plane
     * @param p2 second point in the plane
     * @param p3 third point in the plane
     */
    public Plane(Point p1, Point p2, Point p3){
        _q0=p1;
//        //TODO check direction of vectors
//        Vector U = p1.subtract(p2);
//        Vector V = p3.subtract(p2);

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);

        Vector N = U.crossProduct(V);

        //right hand rule
        _normal =N.normalize();

    }

    /**
     * constructor get point and normal
     * @param p1- point in the plane
     * @param normal- normal of the plane
     */
    public Plane(Point p1,Vector normal){
        _q0=p1;
        _normal =normal.normalize();
    }

    /**
     * implementing {@link Geometry#getNormal(Point)}
     * @param point reference point
     * @return normal vector to the plane
     */
    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }

    /**
     * getter for normal vector
     * @return normal
     */
    public Vector getNormal() {
        return _normal;
    }

    /**
     * getter for q0 referenced point
     * @return the referenced point of the plane
     */
    public Point getQ0() {
        return _q0;
    }

    /**
     *
     * @param r Ray pointing towards the graphic object
     * @return intersections between the ray and the plane
     */


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray r) {
        Vector vectorp0Q;
        try {
            vectorp0Q = this._q0.subtract(r.getP0());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }

        double nv = _normal.dotProduct(r.getDir());
        if (isZero(nv)) { // ray is parallel to the plane - no intersections
            return null;
        }
        double t = alignZero(_normal.dotProduct(vectorp0Q) / nv);

        if ((t <= 0)) {
            return null;
        } else {
            return List.of(new GeoPoint(this, r.getPoint(t)));
        }
    }

}