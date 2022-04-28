package primitives;

import java.util.List;

import static primitives.Util.isZero;
import geometries.Intersectable.GeoPoint;
public class Ray {
    Point _p0;
    Vector _dir;

    /**
     *constructor
     * @param p point of starting the ray
     * @param vec vector of the ray
     */
    public Ray(Point p, Vector vec){
        this._dir = vec.normalize();
        this._p0 =p;
    }

    /**
     * @return string with the data of ray
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + _p0 +
                ", dir=" + _dir +
                '}';
    }

    /**
     * @return P0
     */
    public Point getP0() {
        return _p0;
    }

    /**
     *
     * @return the vector of the ray
     */
    public Vector getDir() {
        return _dir;
    }

    /**
     * calculate point with distance t from P0
     * @param t number to calculate the point
     * @return new point
     */
    public Point getPoint(double t){
        if(isZero(t)){
            return _p0;
        }
        return _p0.add(_dir.scale(t));
    }

    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;

    }
    public GeoPoint findClosestGeoPoint(List<GeoPoint> allGeoPoints){
        double d=0;
        GeoPoint p=null;
        if(allGeoPoints==null)
            return null;

        for (GeoPoint point : allGeoPoints) {
            if (d==0){
                d= point.point.distance(_p0);
                p=point;
            }
            double dCompare= point.point.distance(_p0);
            if (dCompare<d){
                p=point;
            }
        }
        return p;
    }


}
