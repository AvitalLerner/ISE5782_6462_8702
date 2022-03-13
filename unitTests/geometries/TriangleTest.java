package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
        assertEquals(new Vector(sqrtVec,sqrtVec,sqrtVec),triangle.getNormal(new Point(0,0,1)),"incorrect normal to triangle");
    }
}