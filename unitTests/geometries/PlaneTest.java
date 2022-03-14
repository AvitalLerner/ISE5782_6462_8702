package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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

    @Test
    void testFindIntersections() {
        // take from Eliezer
    }
}