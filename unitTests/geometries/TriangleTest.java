package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for{@link Triangle}
 */
class TriangleTest {
    /**
     * Test method for{@link Triangle#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        Triangle triangle=new Triangle(new Point(0,0,1),new Point(1,0,0),new Point(0,1,0));
        double sqrtVec=Math.sqrt(1d/3);
        assertEquals(new Vector(sqrtVec,sqrtVec,sqrtVec),triangle.getNormal(new Point(0,0,1)),
                "incorrect normal to triangle");
    }


    @Test
    void findIntersections() {

        Triangle tr = new Triangle(new Point(0,0,0), new Point(0,1,0), new Point(0,0,1));
        Plane pl = new Plane(new Point(0, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        Ray ray;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside triangle
        ray = new Ray(new Point(-1,-1,-1), new Vector(1,1.1,1.1));
        assertEquals(List.of(new Point(0,0.1,0.1)),tr.findIntersections(ray),
                "findIntersection to point inside the triangle incorrect");

        // TC02: Against edge
        ray= new Ray(new Point(-1,-1,-1),new Vector(1,2,0));
        assertEquals(List.of(new Point(0,1,-1)),pl.findIntersections(ray),
                "intersection of point against edge with plane incorrect");
        assertNull(tr.findIntersections(ray),
                "findIntersections of point against edge is incorrect");

        // TC03: Against vertex
        ray= new Ray(new Point(-2,-2,-2),new Vector(2,1,2.5));
        assertEquals(List.of(new Point(0,-1,0.5)),pl.findIntersections(ray),
                "intersection of point against vertex with plane incorrect");
        assertNull(tr.findIntersections(ray),
                "findIntersections of point against vertex is incorrect");

        // =============== Boundary Values Tests ==================
        // TC11: In vertex
        ray= new Ray(new Point(-1,-1,-1),new Vector(1,1,1));
        assertEquals(List.of(new Point(0,0,0)),pl.findIntersections(ray),
                "intersection of point in vertex with plane incorrect");
        assertNull(tr.findIntersections(ray),
                "findIntersections of point in vertex is incorrect");

        // TC12: On edge
        ray= new Ray(new Point(-1,-1,-1),new Vector(1,1.5,1));
        assertEquals(List.of(new Point(0,0.5,0)),pl.findIntersections(ray),
                "intersection of point on edge with plane incorrect");
        assertNull(tr.findIntersections(ray),
                "findIntersections of point on edge is incorrect");

        // TC13: On edge continuation
        ray= new Ray(new Point(-2,-2,-2),new Vector(2,1,2));
        assertEquals(List.of(new Point(0,-1,0)),pl.findIntersections(ray),
                "intersection of point on edge continuation with plane incorrect");
        assertNull(tr.findIntersections(ray),
                "findIntersections of point on edge continuation is incorrect");
    }
}