package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Polygons
 *
 * @author Dan zilberstein
 *
 */
class PolygonTest {

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)), "Bad normal to triangle");
    }

    /**
     * Test method for
     */
    @Test
    void testFindIntersections() {
        Polygon poly = new Polygon(new Point(0,0,0), new Point(0,0,1), new Point(0,1,1), new Point(0,1,0));
        Plane pl = new Plane(new Point(0, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        Ray ray;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside polygon
        ray = new Ray(new Point(-1,-1,-1), new Vector(1,1.5,1.5));
        assertEquals(List.of(new Point(0,0.5,0.5)),poly.findIntersections(ray),
                "findIntersection to point inside the polygon incorrect");

        // TC02: Against edge
        ray= new Ray(new Point(-1,-1,-1),new Vector(1,2,2));
        assertEquals(List.of(new Point(0,1,-1)),pl.findIntersections(ray),
                "intersection of point against edge with plane incorrect");
        assertNull(poly.findIntersections(ray),
                "findIntersections of point against edge of the polygon is incorrect");

        // TC03: Against vertex
        ray= new Ray(new Point(-2,-2,-2),new Vector(2,3,2.5));
        assertEquals(List.of(new Point(0,-1,0.5)),pl.findIntersections(ray),
                "intersection of point against vertex with plane incorrect");
        assertNull(poly.findIntersections(ray),
                "findIntersections of point against vertex is incorrect");

        // =============== Boundary Values Tests ==================
        // TC11: In vertex
        ray= new Ray(new Point(-1,-1,-1),new Vector(1,1,1));
        assertEquals(List.of(new Point(0,0,0)),pl.findIntersections(ray),
                "intersection of point in vertex with plane incorrect");
        assertNull(poly.findIntersections(ray),
                "findIntersections of point in vertex is incorrect");

        // TC12: On edge
        ray= new Ray(new Point(-1,-1,-1),new Vector(1,1.5,1.5));
        assertEquals(List.of(new Point(0,0.5,0)),pl.findIntersections(ray),
                "intersection of point on edge with plane incorrect");
        assertNull(poly.findIntersections(ray),
                "findIntersections of point on edge is incorrect");

        // TC13: On edge continuation
        ray= new Ray(new Point(-2,-2,-2),new Vector(1,1,1));
        assertEquals(List.of(new Point(-1,-1,-1)),pl.findIntersections(ray),
                "intersection of point on edge continuation with plane incorrect");
        assertNull(poly.findIntersections(ray),
                "findIntersections of point on edge continuation is incorrect");

    }
}