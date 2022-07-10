package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class of cylinder extends Tube.
 * The cylinder have height and 2 bases
 */
public class Cylinder extends Tube {
    /**
     * the height of the cylinder
     */
    double _height;

    /**
     * first base of the cylinder
     */
    Circle _base1;
    /**
     * second base of the cylinder
     */
    Circle _base2;


    /**
     * constructor to initialize the cylinder
     * @param h the high of the cylinder
     * @param radius the ray of the cylinder
     */
    public Cylinder(double h, Ray ray,double radius)
    {
        super(ray,radius);
        this._height =h;
        _base1=new Circle(ray.getP0(),ray.getDir(),ray.getP0(),radius);
        _base2=new Circle(ray.getP0(),ray.getDir(),ray.getPoint(_height),radius);
    }

    /**
     * getter of _height
     * @return _height
     */
    public double getHeight() {
        return _height;
    }

    /**
     * @return string of the data of cylinder
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + _height +
                '}';
    }


    @Override
   protected List<GeoPoint> findGeoIntersectionsHelper(Ray r, double distance) {
        List<GeoPoint> intersections= super.findGeoIntersectionsHelper(r, distance);

        Point bace1Center = _base1.getCenter();
        Point bace2Center = _base2.getCenter();

        double Pythagoras= (Math.pow(_radius,2)+Math.pow(_height,2));

        GeoPoint intersection1;
        GeoPoint intersection2=null;

        //Check if the intersection points is too far from the bases
        if(intersections!=null){
            intersection1=intersections.get(0);
            if(intersections.size()>1)
                intersection2 = intersections.get(1);
            double temp3=bace1Center.distanceSquared(intersection1.point);
            double temp4 = bace2Center.distanceSquared(intersection1.point);
            if((Pythagoras<temp3)
                    ||Pythagoras<temp4)
                intersections.remove(intersection1);

            double temp1=bace1Center.distanceSquared(intersection2.point);
            double temp2 = bace2Center.distanceSquared(intersection2.point);
            if (intersection2!=null && ((Pythagoras < temp1)
                    || Pythagoras < temp2) )
                intersections.remove(intersection2);

            if(intersections!=null&&intersections.size()==2)
                return intersections;
        }

        //calculate the intersection with bases
        List<GeoPoint> intersectionBase1 = _base1.findGeoIntersectionsHelper(r, distance);
        List<GeoPoint> intersectionBase2 = _base2.findGeoIntersectionsHelper(r, distance);

        if (intersectionBase1 != null && intersectionBase2 != null)
            return List.of(intersectionBase1.get(0),intersectionBase2.get(0));

        if (intersectionBase1 != null) {
            if (intersections == null)
                intersections = List.of(intersectionBase1.get(0));
            else
                intersections.add(intersectionBase1.get(0));
        }

        if (intersectionBase2 != null) {
            if (intersections == null)
                intersections = List.of(intersectionBase2.get(0));
            else
                intersections.add(intersectionBase2.get(0));
        }
        return intersections;
    }


    @Override
    public Vector getNormal(Point p1) {
        Point p0= _axisRay.getP0();
        Vector vector= _axisRay.getDir();
        double s;
        try {
            s=alignZero(p1.subtract(p0).dotProduct(vector));
        }
        catch(IllegalArgumentException e){
            return vector;
        }
        if(s==0||isZero(_height -s)){
            return vector;
        }
        p0=p0.add(vector.scale(s));
        return  p1.subtract(p0).normalize();
    }
}