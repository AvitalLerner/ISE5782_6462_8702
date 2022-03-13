package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry  {
    private final Point _q0;
    private final Vector _normal;

    /**
     * constructor get
     * 3 parameters
     * @param p1
     * @param p2
     * @param p3
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
     * @param p1
     * @param normal
     */
    public Plane(Point p1,Vector normal){
        this._q0=p1;
        this._normal =normal.normalize();
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
     * getter for narmal vector
     * @return
     */
    public Vector getNormal() {
        return _normal;
    }
}