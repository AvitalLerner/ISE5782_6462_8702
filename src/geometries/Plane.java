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
     * @param ray Ray pointing towards the graphic object
     * @return intersections between the ray and the plane
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point P0=ray.getP0();
        Vector v=ray.getDir();
        Vector n= _normal;

        double nv=n.dotProduct(v);
        // if ray parallel to plane : no intersection points
        if(isZero(nv)){
            return null;
        }
        if(_q0.equals(P0)){
            return null;
        }
        Vector P0_O= _q0.subtract(P0);
        double t=alignZero(n.dotProduct(P0_O)/nv);
        // origin of ray lay on the plain
        if (isZero(t)){
            return null;
        }

        //if t<0 the direction of the ray points in the direction
        if(t>0){
            Point P=ray.getPoint(t);
            return List.of(P);
        }
        return null;
    }
}