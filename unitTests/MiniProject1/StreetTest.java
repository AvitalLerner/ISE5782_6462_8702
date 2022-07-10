package MiniProject1;
import geometries.Cylinder;
import geometries.Polygon;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;

public class StreetTest {

    @Test
    public void buildStreet(){
        Scene scene = new Scene.SceneBuilder("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.7)))
                .setBackground(new Color(220, 255, 255))
                .build();

        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200)
                .setVPDistance(900)
                .setAperture(null);

        scene.geometries.add(
                //midracha
                new Polygon(new Point(100,-80,100),new Point(-100,-80,100),
                        new Point(-100,-80,200),new Point(100,-80,200)),

                //house
                new Polygon(new Point(90,-80,100),new Point(-90,-80,100)
                        ,new Point(-90,80,100),new Point(90,80,100))
                        .setEmission(new Color(ORANGE)),

                //window
                // new Polygon(new Point(50,0,100),new Point(50,10,100),
                //       new Point(40,10,100),new Point(40,0,100)),

//                //amood
               new Cylinder(80,new Ray(new Point(-60,-80,200),new Vector(0,1,0)),1d)
                       .setEmission(new Color(BLUE)),
//                //menora
                new Sphere(new Point(-60,1,120),3d).setEmission(new Color(WHITE))
        );
        scene.lights.add(new PointLight(new Color(yellow),new Point(-60,1,120)));

        ImageWriter imageWriter = new ImageWriter("MiniProject1", 800, 800);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();

    Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPSize(200, 200)
            .setVPDistance(900)
            .setAperture(null);

        scene.geometries.add(
//                //midracha
//                new Polygon(new Point(100,-80,-100),new Point(-100,-80,-100),
//                        new Point(-100,-80,200),new Point(100,-80,200)),

                //house

                new Polygon(new Point(90,-80,100),new Point(-90,-80,100)
                        ,new Point(-90,80,100),new Point(90,80,100))
                        .setEmission(new Color(ORANGE)).setMaterial(new Material()
                .setKd(0.25)
                .setKs(0.25)
                .setShininess(20)
                .setKt(0.9)),

//               // window
//                 new Polygon(new Point(50,0,100),new Point(50,10,100),
//                       new Point(40,10,100),new Point(40,0,100)),

                //amood
//                new Cylinder(80,new Ray(new Point(-60,-80,120),new Vector(0,1,0)),1d)
//                        .setEmission(new Color(BLUE)),
                //menora
                new Sphere(new Point(-60,1,50),10d)
                .setEmission(new Color(30,30,30))
                .setMaterial(new Material()
                .setKd(0.25)
                .setKs(0.25)
                .setShininess(20)
                .setKt(0.9)
                ));

                // scene.lights.add(new PointLight(new Color(yellow),new Point(-60,1,120)));

                ImageWriter imageWriter = new ImageWriter("MiniProject1", 800, 800);
                camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();

    }
}
