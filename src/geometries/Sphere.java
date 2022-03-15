package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

public class Sphere implements Geometry {
    Point _center;
    double _radius;

    public Sphere(Point center, double radius) {
        _center = center;
        _radius = radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }

    public Point getCenter() {
        return _center;
    }

    public double getRadius() {
        return _radius;
    }

    @Override
    public Vector getNormal(Point p1) {

      return p1.subtract(_center).normalize()  ;
    }

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
          P1=ray.getPoint(t1);
          P2=ray.getPoint(t2);
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


}
