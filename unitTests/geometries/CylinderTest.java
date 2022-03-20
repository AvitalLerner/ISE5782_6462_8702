package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {
    Cylinder _cylinder = new Cylinder(1,new Ray(new Point(0, 0, 1), new Vector(0, -1, 0)));
    Vector normal=_cylinder.getNormal(new Point(0, 0.5, 2)).normalize();
    @Test
    void testGetNormal() {
        double dotProduct = normal.dotProduct(_cylinder.getAxisRay().getDir());
        assertEquals(0,dotProduct,"normal is not orthogonal to the tube");

        boolean normal1=new Vector(0,0,1).equals(normal);
        boolean normal2=new Vector(0,0,-1).equals(normal);
        assertTrue(normal1||normal2,"incorrect normal");
    }

    @Test
    void testFindIntersections() {

    }

}