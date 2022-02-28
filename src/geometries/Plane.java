package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry  {
    Point xyz;
    Vector normal;
    public Plane(Point p1, Point p2, Point p3){
        this.xyz=p1;
        this.normal=null;
    }
    public Plane(Point p1,Vector normal1){
        this.xyz=p1;
        this.normal=normal1.normalize();
    }
    @Override
    public Vector getNormal(Point p1) {
        return null;
    }
    public Vector getNormal() {
        return null;
    }
}
