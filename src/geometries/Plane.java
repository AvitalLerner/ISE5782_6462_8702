package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry  {
    Point xyz;
    Vector normal;

    /**
     * constructor get 3 parameters
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point p1, Point p2, Point p3){
        this.xyz=p1;
        this.normal=null;
    }

    /**
     * constructor get point and normal
     * @param p1
     * @param normal1
     */
    public Plane(Point p1,Vector normal1){
        this.xyz=p1;
        this.normal=normal1.normalize();
    }

    /**
     * calculate the normal
     * @param p1
     * @return
     */
    @Override
    public Vector getNormal(Point p1) {
        return null;
    }
    public Vector getNormal() {
        return null;
    }
}