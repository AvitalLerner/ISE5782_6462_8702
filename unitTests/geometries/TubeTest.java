package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void testGetNormal() {
      Tube tube=new Tube(new Ray(new Point(1,0,0),new Vector(0,1,0)),1);
      Vector normal=tube.getNormal(new Point(2,0.5,0)).normalize();
      double dotProduct= normal.dotProduct(tube.getAxisRay().getDir());
      assertEquals(0,dotProduct,"normal is not orthogonal to the tube");

      boolean normal1=new Vector(1,0,0).equals(normal);
      boolean normal2=new Vector(-1,0,0).equals(normal);
      assertTrue(normal1||normal2,"incorrect normal");

     }
}