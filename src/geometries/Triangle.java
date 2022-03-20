package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public class Triangle extends Polygon implements Geometry{
    /**
     * constructor get 3 point of the triangle
     *
     * @param p1 first point of the triangle
     * @param p2 second point of the triangle
     * @param p3 third point of the triangle
     */
    public Triangle(Point p1,Point p2,Point p3){
        super(p1,p2,p3);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + _plane +
                '}';
    }

    /**
     *
     * @param ray Ray pointing towards the graphic object
     * @return intersection between the triangle and the ray
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }
}
