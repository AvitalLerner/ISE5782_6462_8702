package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

public class Sphere extends Geometry {
    Point _center;
    double _radius;

    /**
     * constructor
     * @param center- the center of the sphere
     * @param radius- the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        _center = center;
        _radius = radius;
    }

    /**
     * @return string of the data of the sphere
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }

    /**
     * @return center
     */
    public Point getCenter() {
        return _center;
    }

    /**
     * @return radius
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * @param p1 point to calculate the normal
     * @return normal
     */
    @Override
    public Vector getNormal(Point p1) {

      return p1.subtract(_center).normalize()  ;
    }

    /**
     * @param ray Ray pointing towards the graphic object
     * @return intersection between the ray and the sphere
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
      Point p0=ray.getP0();
      Vector v=ray.getDir();

      if(p0.equals(_center)){
          return  List.of(_center.add(v.scale(_radius)));
      }
      Vector u=_center.subtract(p0);
      double tm=alignZero(v.dotProduct(u));

      double d=Math.sqrt(u.lengthSquared()-(tm*tm));
      double th=Math.sqrt(_radius*_radius-d*d);
      //there are no intersections
      if(d>=_radius){
          return null;
      }
      double t1=alignZero(tm-th);
      double t2=alignZero(tm+th);

      Point P1;
      Point P2;

      if(t1>0&&t2>0){
          P1=p0.add(v.scale(t1));
          P2=p0.add(v.scale(t2));
         // P1=ray.getPoint(t1);
         // P2=ray.getPoint(t2);
          return List.of(P1,P2);
      }
      if(t1>0){
            P1=ray.getPoint(t1);
            return List.of(P1);
        }
        if(t2>0){
            P2=ray.getPoint(t2);
            return List.of(P2);
        }
        return null;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray r) {
        return null;
    }
}