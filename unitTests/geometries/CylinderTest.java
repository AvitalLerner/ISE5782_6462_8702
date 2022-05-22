package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {
    Cylinder _cylinder = new Cylinder(1,new Ray(new Point(0, 0, 1), new Vector(0, -1, 0)));
    Vector normal=_cylinder.getNormal(new Point(0, 0.5, 2)).normalize();

    /**
     * Test method for{@link Cylinder#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        double dotProduct = normal.dotProduct(_cylinder.getAxisRay().getDir());
        assertEquals(0,dotProduct,"normal is not orthogonal to the tube");

        boolean normal1=new Vector(0,0,1).equals(normal);
        boolean normal2=new Vector(0,0,-1).equals(normal);
        assertTrue(normal1||normal2,"incorrect normal");
    }

    /**
     * Test method for{@link Cylinder#findIntersections(Ray)}
     */
    @Test
    void testFindIntersections() {
      Cylinder cylinder1=new Cylinder(1,new Ray(new Point(1, 0, 0), new Vector(0, 1, 0)));
      Cylinder cylinder2=new Cylinder(1,new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the cylinder
        assertNull(cylinder1.findIntersections(new Ray(new Point(1, 1, 2), new Vector(1, 1, 0))),
              "there aren't supposed to be intersections");

        //// TC02: Ray's crosses the cylinder
        List<Point> result =cylinder2.findIntersections(new Ray(new Point(0, 0, 0), new Vector(2, 1, 1)));
        assertEquals(2, result.size(),
                "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY()) {
            result = List.of(result.get(1), result.get(0));
        }
        assertEquals(List.of(new Point(0.4, 0.2, 0.2), new Point(2, 1, 1)), result,
                "Intersections points incorrect");

        // TC03: Ray's starts within cylinder and crosses the cylinder
        result = cylinder2.findIntersections( new Ray(new Point(1, 0.5, 0.5), new Vector(2, 1, 1)));

        assertEquals(1, result.size(),
                "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1)), result,
                "Intersection point calculated wrong");

    }

}