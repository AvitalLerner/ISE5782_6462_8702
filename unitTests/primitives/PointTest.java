package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Point}
 */
class PointTest {
    Point p1 = new Point(1, 2, 3);
    @Test
    void testSubtract() {
        assertEquals(new Vector(1,1,1),new Point(2,3,4).subtract(p1),"ERROR: Point - Point does not work correctly");
      }

    @Test
    void testAdd() {
        fail("not yet implemented");
    }

    @Test
    void testDistanceSquared() {
    }

    /**
     * testing the {@link Point#distance(Point)}
     */
    @Test
    void testDistance() {
        Point point3 = new Point(0.5, 0, -100);
        double distance = point3.distance(new Point(0, 0, -100));
        assertEquals(0.5, distance, 0.0001, "wrong ");
    }
}