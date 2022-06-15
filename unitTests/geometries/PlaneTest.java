package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for{@link Plane}
 */
class PlaneTest {

    /**
     * Test method for{@link Plane#getNormal(Point)}
     */
    @Test
    public void testTestGetNormal() {
        Plane plane=new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrtVec=Math.sqrt(1d/3);
        assertEquals(new Vector(sqrtVec,sqrtVec,sqrtVec),
                plane.getNormal(new Point(0,1,0)),"incorrect normal to plane");
    }

    /**
     * Test method for{@link Plane#findIntersections(Ray)}
     */
    @Test
    void testFindIntersections() {
        Plane pl = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane
        assertEquals(List.of(new Point(1, 0, 0)),
                pl.findIntersections(new Ray(new Point(0.5, 0, 0) ,new Vector(1, 0, 0))),
                "there is supposed to be a plane intersection");

        // TC02: Ray does not intersect the plane
        assertNull(pl.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "no intersection supposed to be with plane");

        // =============== Boundary Values Tests ==================
        // TC11: Ray parallel to plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, 1, -1))),
                "Must not be plane intersection ray is parallel to plane");

        // TC12: Ray included in plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(0, 1, -1))),
                "Must not be plane intersection ray is included in the plane ");

        // TC13: Orthogonal ray into plane
        assertEquals(List.of(new Point(1d / 3, 1d / 3, 1d / 3)),
                pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-1, -1, -1))),
                "there is supposed to be a plane intersection with Orthogonal ray");

        // TC14: Orthogonal ray out of plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC15: Orthogonal ray from plane
            assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 1))),
               "Must not be plane intersection point from plane and Orthogonal");

        // TC16: Ray from plane not Orthogonal or parallel
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 0))),
                "Must not be plane intersection ray from plane");

        // TC17: Ray from plane's Q point
        assertNull(pl.findIntersections(new Ray(new Point(0, 0, 1), new Vector(1, 1, 0))),
                "Must not be plane intersection ray from point Q");
    }
}