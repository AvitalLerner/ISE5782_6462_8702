package MiniProject1;
import geometries.*;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
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
                //moon
                new Sphere(new Point(-120,110,-200),18d)
                        .setEmission(new Color(30,30,30))
                        .setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)
                                .setKt(0.9)),
//                new Sphere(new Point(-140,1,60),10d)
//                        .setEmission(new Color(scene._background.getColor()))
//                        .setMaterial(new Material()
//                                        .setKd(0.25)
//                                        .setKs(0.25)
//                                        .setShininess(20)),
                //midracha
                new Polygon(new Point(100,-80,-100),new Point(-100,-80,-100),
                        new Point(-100,-80,200),new Point(100,-80,200))
                        .setEmission(new Color(84,1,16)).setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)),
                new Polygon(new Point(100,-72,200),new Point(-100,-72,200),
                        new Point(-100,-80,200),new Point(100,-80,200))
                        .setEmission(new Color(64,1,12)).setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)),
                // water puddle

                new Circle(new Point(-40,-65,201),new Vector(0,-5,2),7).setEmission(new Color(30,30,30))
                        .setMaterial(new Material()
                                .setKr(0.9)),
                new Circle(new Point(-35,-65,201),new Vector(0,-5,2),10).setEmission(new Color(30,30,30))
                        .setMaterial(new Material()
                                .setKr(0.9)),
                new Circle(new Point(-20,-65,201),new Vector(0,-5,2),15).setEmission(new Color(30,30,30))
                        .setMaterial(new Material()
                                .setKr(0.9)),

                //house

                new Polygon(new Point(100,-80,-100),new Point(-100,-80,-100)
                        ,new Point(-100,80,-100),new Point(100,80,-100))
                        .setEmission(new Color(122,2,24)).setMaterial(new Material()
                                        .setKd(0.25)
                                        .setKs(0.25)
                                        .setShininess(20)),
                // roof

                new Polygon(new Point(-100,100,-100),new Point(100,100,-100),new Point(120,70,-90),new Point(-120,70,-90))
                        .setEmission(new Color(64,1,12)).setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)),
                // door closed

                new Polygon(new Point(20,-80,-99)
                        ,new Point(20,-10,-99)
                        ,new Point(-20,-10,-99)
                        ,new Point(-20,-80,-99)).setEmission(new Color(150, 75, 0)),



//               // window
                new Circle(new Point(35,30,90),new Vector(0,0,-1),15)
                        .setEmission(new Color(64,1,12)).setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)),
                 new Polygon(new Point(50,-15,100),new Point(50,30,100),
                       new Point(20,30,100),new Point(20,-15,100))
                         .setEmission(new Color(84,1,16)).setMaterial(new Material()
                                         .setKd(0.25)
                                         .setKs(0.25)
                                         .setShininess(20)),
                new Polygon(new Point(35,8,110),new Point(35,25,110),
                        new Point(25,25,110),new Point(25,8,110))
                        .setEmission(new Color(30,30,30))
                        .setMaterial(new Material()
                                        .setKd(0.25)
                                        .setKs(0.25)
                                        .setShininess(20)
                                        .setKt(0.9)),
                new Polygon(new Point(35,-10,110),new Point(35,7,110),
                        new Point(25,7,110),new Point(25,-10,110))
                        .setEmission(new Color(30,30,30))
                        .setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)
                                .setKt(0.9)),
                new Polygon(new Point(36,8,110),new Point(36,25,110),
                        new Point(46,25,110),new Point(46,8,110))
                        .setEmission(new Color(30,30,30))
                        .setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)
                                .setKt(0.9)),
                new Polygon(new Point(36,-10,110),new Point(36,7,110),
                        new Point(46,7,110),new Point(46,-10,110))
                        .setEmission(new Color(30,30,30))
                        .setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)
                                .setKt(0.9)),
                //grass
                new Triangle(new Point(-50,-80,-100),new Point(-150,-80,-100),new Point(-150,-100,200))
                        .setEmission(new Color(green))
                        .setMaterial(new Material()
                        .setKd(0.25)
                        .setKs(0.25)
                        .setShininess(20)),

                new Triangle(new Point(50,-80,-100),new Point(150,-80,-100),new Point(150,-100,200))
                        .setEmission(new Color(green))
                        .setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)),

                //amood
//                new Cylinder(80,new Ray(new Point(-60,-80,120),new Vector(0,1,0)),1d)
//                        .setEmission(new Color(BLUE)),
                //light lamp
                new Sphere(new Point(-60,1,50),10d)
                .setEmission(new Color(30,30,30))
                .setMaterial(new Material()
                .setKd(0.25)
                .setKs(0.25)
                .setShininess(20)
                .setKt(0.9)),
                new Polygon(new Point(-63,-9,60),new Point(-57,-9,60)
                        ,new Point(-57,-80,60),new Point(-63,-80,60))
                        .setEmission(new Color(3,13,84))
                        .setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)),
                new Sphere(new Point(-52.5,-75,180),6).setEmission(new Color(3,13,84))
                        .setMaterial(new Material()
                        .setKd(0.25)
                        .setKs(0.25)
                        .setShininess(20))

        );

               scene.lights.add(new PointLight(new Color(yellow),new Point(-60,1,120)).setKl(4E-5).setKq(2E-7));
                scene.lights.add(new SpotLight(new Color(800, 500, 250),new Point(35,25,105),new Vector(0,-1,0)).setKl(0.001).setKl(0.0001));
               scene.lights.add(new PointLight(new Color(yellow),new Point(-120,110,-200)).setKl(4E-5).setKq(2E-7));

                ImageWriter imageWriter = new ImageWriter("MiniProject1", 800, 800);
                camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();

    }

}
