package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void testFindIntersections() {
        Geometries listOfObjects=new Geometries();
        Sphere sphere = new Sphere( new Point(1, 0, 0),1d);
        Triangle tr = new Triangle(new Point(0,0,0), new Point(0,1,0), new Point(0,0,1));
        Polygon polygon=new Polygon(new Point(0,1,0),new Point(0,0,0),new Point(0,0,1),new Point(0,1,1));
        Tube tube=new Tube(new Ray(new Point(0, 0, 1), new Vector(0, -1, 0)),1.0);
        Plane plane=new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));
        // =============== Boundary Values Tests ==================

        //TC11: there are no objects to intersect
        assertNull(listOfObjects.findIntersections(new Ray(new Point(0.5, 0, 0)
               ,new Vector(1, 0, 0))),"no object to intersect");

        //TC12: there is no object that has intersection with ray
        listOfObjects.add(sphere,plane);
        assertNull(listOfObjects.findIntersections(new Ray(new Point(-1,-1,-1),
                new Vector(-3,-3,-3))),"no intersection supposed to be");
        //TC13: only one object intersects
        assertEquals(List.of(new Point(1,1,0)),listOfObjects.findIntersections(new Ray(new Point(0.5,0.5,0),new Vector(0.5,0.5,0))),"one point of intersection only with sphere");

        //TC14: not all objects intersect with ray
    }
}