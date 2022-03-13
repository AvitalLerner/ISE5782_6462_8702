package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Cylinder extends Tube {
    double height;

    /**
     * constructor to initialize the cylinder
     *
     * @param h the high of the cylinder
     * @param r the ray of the cylinder
     */
    public Cylinder(double h, Ray r){
        super(r,h);
      this.height=h;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                '}';
    }

    @Override
    public Vector getNormal(Point p1) {
        Point p0=axisRay.getP0();
        Vector vector=axisRay.getDir();
        double s;
        try {
            s=alignZero(p1.subtract(p0).dotProduct(vector));
        }catch(IllegalArgumentException e){
            return vector;
        }
        if(s==0||isZero(height-s)){
            return vector;
        }
        p0=p0.add(vector.scale(s));
        return  p1.subtract(p0).normalize();
    }
}