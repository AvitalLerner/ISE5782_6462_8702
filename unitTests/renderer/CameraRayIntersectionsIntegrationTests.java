package renderer;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

class CameraRayIntersectionsIntegrationTests {

    /**
     *      * Test helper function to count the intersections and compare with expected value
     * @param cam
     * @param geo
     * @param expected
     */
    private void assertCountIntersections(Camera cam, Intersectable geo, int expected) {
        List<Point> allPoints=null;
        for(int i=0; i<3;i++){
            for(int j=0; j<3;j++){
               Ray ray=cam.constructRayThroughPixel(3,3,j,i);
               List<Point> lst=geo.findIntersections(ray);
               if(lst!=null){
                   if (allPoints==null){
                   allPoints=new LinkedList<>();
                   }
                   allPoints.addAll(lst);
               }
            }
        }
        assertEquals(expected,allPoints.size(),"num of intersection points aren't enough ");
    }
    /**
     *
     */
    @Test
    public void cameraRaySphereIntegration() {
        Sphere sphere=new Sphere(new Point(0,0,-3),1);
        Camera camera=new Camera(new Point(0,0,0),new Vector(0,0,-1),new Vector(0,1,0))
                .setVPDistance(1).setVPSize(3,3);
        // TC01: Small Sphere 2 points
        assertCountIntersections(camera,sphere,2);
        // TC02: Big Sphere 18 points
       sphere=new Sphere(new Point(0,0,-2.5),2.5);
        assertCountIntersections(camera,sphere,18);
        // TC03: Medium Sphere 10 points
       camera=new Camera(new Point(0,0,0.5),new Vector(0,0,-1),new Vector(0,1,0));
        sphere=new Sphere(new Point(0,0,-2),2);
        assertCountIntersections(camera,sphere,10);
        // TC04: Inside Sphere 9 points
        //sphere=new Sphere(new Point(0,0,-3),3);
       // assertCountIntersections(camera,sphere,9);
        // TC05: Beyond Sphere 0 points
        camera=new Camera(new Point(0,0,0),new Vector(0,0,-1),new Vector(0,1,0));
        sphere=new Sphere(new Point(0,0,1),1);
        assertCountIntersections(camera,sphere,0);

    }

    @Test
    public void cameraRayPlaneIntegration() {

        // TC01: Plane against camera 9 points
        Camera camera=new Camera(new Point(0,0,0),new Vector(0,0,-1),new Vector(0,1,0));
        Plane plane=new Plane(new Point(0,0,4),new Vector(0,0,3));
        assertCountIntersections(camera,plane,9);
        // TC02: Plane with small angle 9 points

        // TC03: Plane parallel to lower rays 6 points

        // TC04: Beyond Plane 0 points
    }

    /**
     *
     */
    @Test
    public void cameraRayTriangleIntegration() {

        // TC01: Small triangle 1 point

        // TC02: Medium triangle 2 points
    }

}
