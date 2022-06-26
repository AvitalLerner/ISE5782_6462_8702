package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class of cylinder extends Tube.
 * The cylinder have height
 */
public class Cylinder extends Tube {
    /**
     * the height of the cylinder
     */
    double _height;

    Circle _base1;
    Circle _base2;


    /**
     * constructor to initialize the cylinder
     * @param h the high of the cylinder
     * @param radius the ray of the cylinder
     */
    public Cylinder(double h, Ray ray,double radius )
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

        GeoPoint intersection1=null;
        GeoPoint intersection2=null;
        GeoPoint intersection3=null;
        GeoPoint intersection4=null;

        //Check if the intersection points is too far from the bases
        if(intersections.size()>0){
            intersection1=intersections.get(0);
            if((Pythagoras<Math.pow(bace1Center.distance(intersection1.point),2))
                    ||Pythagoras<Math.pow(bace2Center.distance(intersection1.point),2))
                intersection1=null;

        }
        if(intersections.size()>1) {
            intersection2 = intersections.get(1);
            if ((Pythagoras < Math.pow(bace1Center.distance(intersection2.point), 2))
                    || Pythagoras < Math.pow(bace2Center.distance(intersection2.point), 2))
                intersection2=null;
        }

        //calculate the intersection with base1
        List<GeoPoint> intersectionBase1=_base1.findGeoIntersectionsHelper(r, distance);
        if(intersectionBase1.size()==1)
            intersection3=intersectionBase1.get(0);

        //calculate the intersection with base2
        List<GeoPoint> intersectionBase2=_base2.findGeoIntersectionsHelper(r, distance);
        if(intersectionBase2.size()==1)
            intersection4=intersectionBase2.get(0);


        List<GeoPoint> result =null;
        if(intersection1!=null)
            result.add(intersection1);
        if(intersection2!=null)
            result.add(intersection2);
        if(intersection3!=null)
            result.add(intersection3);
        if(intersection4!=null)
            result.add(intersection4);

        return result;
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