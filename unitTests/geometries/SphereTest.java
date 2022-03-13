package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
     Sphere sphere= new Sphere(new Point(1,0,0),1);
     assertEquals(new Vector(1,0,0),sphere.getNormal(new Point(2,0,0)));
    }
}