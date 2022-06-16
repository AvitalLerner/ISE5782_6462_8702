package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Point}
 */
class PointTest {
    Point p1 = new Point(1, 2, 3);

    /**
     * Test method for{@link Point#subtract(Point)}
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals(new Vector(1,1,1),new Point(2,3,4).subtract(p1),"ERROR: Point - Point does not work correctly");
      }

    /**
     * Test method for{@link Point#add(Vector)}
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals(new Point(2,3,4),p1.add(new Vector(1,1,1)),"Error: Point + Point does not work");
    }

    /**
     * Test method for{@link Point#distanceSquared(Point)}
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point point3 = new Point(0.5, 0, -100);
        double distance = point3.distanceSquared(new Point(0, 0, -100));
        assertEquals(0.25, distance, 0.0001,
                "Doesn't calculate correctly the squared distance ");
    }

    /**
     * testing the {@link Point#distance(Point)}
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point point3 = new Point(0.5, 0, -100);
        double distance = point3.distance(new Point(0, 0, -100));
        assertEquals(0.5, distance, 0.0001, "Doesn't calculate correctly the distance");
    }
}