package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void findClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 10, -100));

        // TC1: The nearest point is in the middle of the list
        List<Point> list1 = List.of(
                new Point(0, 0, 2),
                new Point(0, 0, 1),
                new Point(10, 3, 1));

        assertEquals(new Point(0, 0, 1), ray.findClosestPoint(list1));

        // TC2: The list is null
        List<Point> list2 = null;

        assertNull(ray.findClosestPoint(list2));

        // TC01: The nearest point is the first point of the list
        List<Point> list3 = List.of(
                new Point(0, 0, 1),
                new Point(0, 0, 2),
                new Point(10, 3, 1));

        assertEquals(new Point(0, 0, 1), ray.findClosestPoint(list3));

        // TC01: The nearest point is last point of the list
        List<Point> list4 = List.of(
                new Point(0, 0, 2),
                new Point(10, 3, 1),
                new Point(0, 0, 1));

        assertEquals(new Point(0, 0, 1), ray.findClosestPoint(list4));
    }
}