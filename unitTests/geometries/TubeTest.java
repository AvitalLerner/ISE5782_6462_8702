package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Tube}
 */
class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube tube = new Tube(new Ray(new Point(0, 0, 1), new Vector(0, -1, 0)),1.0);
        Vector normal = tube.getNormal(new Point(0, 0.5, 2)).normalize();
        double dotProduct = normal.dotProduct(tube.getAxisRay().getDir());
        //TC01: normal orthogonal to the tube
        assertEquals(0,dotProduct,"normal is not orthogonal to the tube");

        //check that the normal is right
        boolean normal1=new Vector(0,0,1).equals(normal);
        boolean normal2=new Vector(0,0,-1).equals(normal);
        assertTrue(normal1||normal2,"incorrect normal");

     }

    /**
     * Test method for {@link Tube#findIntersections(Ray)}
     * bonus
     */
    @Test
    void testFindIntersections() {
        Tube tube1 = new Tube( new Ray(new Point(1, 0, 0), new Vector(0, 0, 1)),1);
        Ray ray;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the tube (0 points)
        ray=new Ray(new Point(3,0,1),new Vector(0,1,1));
        assertNull(tube1.findGeoIntersection(ray),"Must not be intersections");

        // TC02: Ray's crosses the tube (2 points)
        ray=new Ray(new Point(2,2,0),new Vector(-1,-1,1));
        assertEquals(List.of(new Point(0,0,2),new Point(1,1,1)),tube1.findIntersections(ray),
               "incorrect intersection");

        // TC03: Ray's starts within tube and crosses the tube (1 point)
        ray=new Ray(new Point(0.5,0,1),new Vector(-0.5,0,1));
        assertEquals(List.of(new Point(0,0,2)),tube1.findIntersections(ray),
                "incorrect intersection");


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line is parallel to the axis (0 points)
        // TC11: Ray is inside the tube (0 points)
        ray=new Ray(new Point(0.5,0,1),new Vector(0,0,1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC12: Ray is outside the tube (0 point)
        ray=new Ray(new Point(3,0,1),new Vector(0,0,1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC13: Ray is at the tube surface (0 point)
        ray=new Ray(new Point(2,0,1),new Vector(0,0,1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC14: Ray is inside the tube and starts against axis head (0 point)
        ray=new Ray(new Point(0.5,0,0),new Vector(0,0,1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC15: Ray is outside the tube and starts against axis head (0 point)
        ray=new Ray(new Point(3,0,0),new Vector(0,0,1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC16: Ray is at the tube surface and starts against axis head (0 point)
        ray=new Ray(new Point(2,0,0),new Vector(0,0,1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC17: Ray is inside the tube and starts at axis head (0 point)
        ray=new Ray(new Point(1,0,0),new Vector(0,0,1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // **** Group: Ray is orthogonal but does not begin against the axis head
        // TC21: Ray starts outside and the line is outside (0 points)
        ray=new Ray(new Point(3,0,1),new Vector(0,1,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC22: The line is tangent and the ray starts before the tube (0 points)
        ray=new Ray(new Point(0,1,1),new Vector(1,0,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC23: The line is tangent and the ray starts at the tube (0 points)
        ray=new Ray(new Point(1,1,1),new Vector(1,0,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC24: The line is tangent and the ray starts after the tube (0 points)
        ray=new Ray(new Point(3,1,1),new Vector(1,0,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC25: Ray starts before (2 points)
        ray=new Ray(new Point(0.5,-1,1),new Vector(0,1,0));
        assertEquals(List.of(new Point(0.5,Math.sqrt(0.75),1),new Point(0.5,-Math.sqrt(0.75),1)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC26: Ray starts at the surface and goes inside (1 point)
        ray=new Ray(new Point(0.5,-Math.sqrt(0.75),1),new Vector(0,1,0));
        assertEquals(List.of(new Point(0.5,Math.sqrt(0.75),1)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC27: Ray starts inside (1 point)
        ray=new Ray(new Point(0.5,0,1),new Vector(0,1,0));
        assertEquals(List.of(new Point(0.5,Math.sqrt(0.75),1)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC28: Ray starts at the surface and goes outside (0 points)
        ray=new Ray(new Point(0.5,Math.sqrt(0.75),1),new Vector(0,1,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC29: Ray starts after (0 point)
        ray=new Ray(new Point(0.5,1,1),new Vector(0,1,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC30: Ray starts before and crosses the axis (2 points)
        ray=new Ray(new Point(-1,0,1),new Vector(1,0,0));
        assertEquals(List.of(new Point(2,0,1),new Point(0,0,1)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC31: Ray starts at the surface and goes inside and crosses the axis (1 point)
        ray=new Ray(new Point(0,0,1),new Vector(1,0,0));
        assertEquals(List.of(new Point(2,0,1)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC32: Ray starts inside and the line crosses the axis (1 point)
        ray=new Ray(new Point(0.5,0,1),new Vector(1,0,0));
        assertEquals(List.of(new Point(2,0,1)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC33: Ray starts at the surface and goes outside and the line crosses the axis (0 points)
        ray=new Ray(new Point(2,0,1),new Vector(1,0,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC34: Ray starts after and crosses the axis (0 points)
        ray=new Ray(new Point(3,0,1),new Vector(1,0,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC35: Ray starts at the axis (1 point)
        ray=new Ray(new Point(1,0,1),new Vector(1,0,0));
        assertEquals(List.of(new Point(2,0,1)),tube1.findIntersections(ray),
                "incorrect intersection");

        // **** Group: Ray is orthogonal to axis and begins against the axis head
        // TC41: Ray starts outside and the line is outside (0 point)
        ray=new Ray(new Point(3,0,0),new Vector(1,0,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC42: The line is tangent and the ray starts before the tube (0 point)
        ray=new Ray(new Point(0,1,0),new Vector(1,0,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC43: The line is tangent and the ray starts at the tube (0 point)
        ray=new Ray(new Point(1,1,0),new Vector(1,0,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC44: The line is tangent and the ray starts after the tube (0 point)
        ray=new Ray(new Point(3,1,0),new Vector(1,0,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC45: Ray starts before (2 points)
        ray=new Ray(new Point(0.5,-1,0),new Vector(0,1,0));
        assertEquals(List.of(new Point(0.5,Math.sqrt(0.75),0),new Point(0.5,-Math.sqrt(0.75),0)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC46: Ray starts at the surface and goes inside (1 point)
        ray=new Ray(new Point(0.5,-Math.sqrt(0.75),0),new Vector(0,1,0));
        assertEquals(List.of(new Point(0.5,Math.sqrt(0.75),0)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC47: Ray starts inside (1 point)
        ray=new Ray(new Point(0.5,0,0),new Vector(0,1,0));
        assertEquals(List.of(new Point(0.5,Math.sqrt(0.75),0)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC48: Ray starts at the surface and goes outside (0 point)
        ray=new Ray(new Point(0.5,Math.sqrt(0.75),0),new Vector(0,1,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC49: Ray starts after (0 point)
        ray=new Ray(new Point(0.5,1,0),new Vector(0,1,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC50: Ray starts before and goes through the axis head (2 points)
        ray=new Ray(new Point(-1,0,0),new Vector(1,0,0));
        assertEquals(List.of(new Point(2,0,0),new Point(0,0,0)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC51: Ray starts at the surface and goes inside and goes through the axis head (1 point)
        ray=new Ray(new Point(0,0,0),new Vector(1,0,0));
        assertEquals(List.of(new Point(2,0,0)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC52: Ray starts inside and the line goes through the axis head (1 point)
        ray=new Ray(new Point(0.5,0,0),new Vector(1,0,0));
        assertEquals(List.of(new Point(2,0,0)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC53: Ray starts at the surface and the line goes outside and goes through the axis head (0 point)
        ray=new Ray(new Point(2,0,0),new Vector(1,0,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC54: Ray starts after and the line goes through the axis head (0 point)
        ray=new Ray(new Point(3,0,0),new Vector(1,0,0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC55: Ray starts at the axis head (1 point)
        ray=new Ray(new Point(1,0,0),new Vector(1,0,0));
        assertEquals(List.of(new Point(2,0,0)),tube1.findIntersections(ray),
                "incorrect intersection");

        // **** Group: Ray's line is neither parallel nor orthogonal to the axis and begins against axis head
        // TC62: Ray's line crosses the tube and begins before (2 points)
        ray = new Ray(new Point( 0,1,0), new Vector(1.6, -0.8, 0.8));
        assertEquals(List.of(new Point(2, 0, 1), new Point(0.4, 0.8, 0.2)), tube1.findIntersections(ray),
                "incorrect intersection");

        // TC63: Ray's line crosses the tube and begins at surface and goes inside (1 point)
        ray = new Ray(new Point( 2,0,0), new Vector(-1.6, 0.8, 0.2));
        assertEquals(List.of( new Point(0.4, 0.8, 0.2)), tube1.findIntersections(ray),
                "incorrect intersection");

        // TC64: Ray's line crosses the tube and begins inside (1 point)
        ray = new Ray(new Point( 1.5,0.5,0), new Vector(0.5, -0.5, 0));
        assertEquals(List.of(new Point(2, 0, 0)), tube1.findIntersections(ray),
                "incorrect intersection");

        // TC65: Ray's line crosses the tube and begins at the axis head (1 point)
        ray = new Ray(new Point( 1, 0, 0), new Vector(1, 0, 1));
        assertEquals(List.of(new Point(2,0,1)), tube1.findIntersections(ray),
                "incorrect intersection");

        // TC66: Ray's line crosses the tube and begins at surface and goes outside (0 point)
        ray=new Ray(new Point(2,0,0),new Vector(1,1,1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC67: Ray's line is tangent and begins before (0 point)
        ray=new Ray(new Point(2,-1,0),new Vector(0,1,1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC68: Ray's line is tangent and begins at the tube surface (0 point)
        ray=new Ray(new Point(2,0,0),new Vector(0,1,1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC69: Ray's line is tangent and begins after (0 point)
        ray=new Ray(new Point(2,1,0),new Vector(0,1,1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // **** Group: Ray's line is neither parallel nor orthogonal to the axis and does not begin against axis head
        // TC71: Ray's crosses the tube and the axis (2 points)
        ray = new Ray(new Point( 3, 0, -1), new Vector(-1, 0, 2));
        assertEquals(List.of( new Point(0,0,5),new Point(2,0,1)), tube1.findIntersections(ray),
                "incorrect intersection");

        // TC72: Ray's crosses the tube and the axis head (2 points)
        ray = new Ray(new Point(-0.2,1.6,2 ), new Vector(0.6,-0.8,-1));
        assertEquals(List.of( new Point(1.6,-0.8,-1),new Point(0.4,0.8,1)), tube1.findIntersections(ray),
                "incorrect intersection");

        // TC73: Ray's begins at the surface and goes inside (1 point)
        ray=new Ray(new Point(0.4,0.8,2),new Vector(1.1,-Math.sqrt(0.75)-0.8,-1));
        assertEquals(List.of(new Point(1.5,-Math.sqrt(0.75),1)),tube1.findIntersections(ray),
                "incorrect intersection");

        // TC74: Ray's begins at the surface and goes inside crossing the axis (1 point)
        ray = new Ray(new Point(0.4,0.8,2), new Vector(0.6,-0.8,-1));
        assertEquals(List.of( new Point(1.6,-0.8,0)), tube1.findIntersections(ray),
                "incorrect intersection");

        // TC75: Ray's begins at the surface and goes inside crossing the axis head (1 point)
        ray = new Ray(new Point(0.4,0.8,1), new Vector(0.6,-0.8,-1));
        assertEquals(List.of( new Point(1.6,-0.8,-1)), tube1.findIntersections(ray),
                "incorrect intersection");

        // TC76: Ray's begins inside and the line crosses the axis (1 point)
        ray = new Ray(new Point(0.94,0.08,1.1), new Vector(0.6,-0.8,-1));
        assertEquals(List.of( new Point(1.6,-0.8,0)), tube1.findIntersections(ray),
                "incorrect intersection");

        // TC77: Ray's begins inside and the line crosses the axis head (1 point)
        ray = new Ray(new Point(0.94,0.08,0.1), new Vector(0.6,-0.8,-1));
        assertEquals(List.of( new Point(1.6,-0.8,-1)), tube1.findIntersections(ray),
                "incorrect intersection");

        // TC78: Ray's begins at the axis (1 point)
        ray = new Ray(new Point(1,0,0), new Vector(0.6,-0.8,-1));
        assertEquals(List.of( new Point(1.6,-0.8,-1)), tube1.findIntersections(ray),
                "incorrect intersection");

         // TC79: Ray's begins at the surface and goes outside (0 point)
        ray=new Ray(new Point(1.6,-0.8,-1),new Vector(0.6,-0.7,-1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC80: Ray's begins at the surface and goes outside and the line crosses the axis (0 point)
        ray=new Ray(new Point(1.6,-0.8,-1),new Vector(0.6,-0.8,-2));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC81: Ray's begins at the surface and goes outside and the line crosses the axis head (0 point)
        ray=new Ray(new Point(1.6,-0.8,-1),new Vector(0.6,-0.8,-1));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");
    }
}