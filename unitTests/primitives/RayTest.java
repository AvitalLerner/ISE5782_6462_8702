package primitives;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void findClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 10, -100));
        List<Point> list1 = List.of(
                new Point(0, 0, 2),
                new Point(0, 0, 1),
                new Point(10, 3, 1));

        assertEquals(new Point(0, 0, 1), ray.findClosestPoint(list1));
        List<Point> list2 = null;

        assertNull(ray.findClosestPoint(list2));
        List<Point> list3 = List.of(
                new Point(0, 0, 1),

                new Point(0, 0, 2),
                new Point(10, 3, 1));

        assertEquals(new Point(0, 0, 1), ray.findClosestPoint(list3));


        List<Point> list4 = List.of(
                new Point(0, 0, 2),
                new Point(10, 3, 1),
                new Point(0, 0, 1));

        assertEquals(new Point(0, 0, 1), ray.findClosestPoint(list4));



    }
}