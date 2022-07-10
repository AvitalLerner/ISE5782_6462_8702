package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract class rayTracer that renderer the scene
 */
public abstract class RayTracer {

    /**
     * the scene of the picture
     */
    protected Scene scene;//was final

    /**
     * constructor
     * @param scene the scene of the class
     */
    public RayTracer(Scene scene){
        this.scene = scene;
    }

    /**
     * function that calculate the color where the ray hits
     * @param ray
     * @return color of pixel
     */
    public abstract Color traceRay(Ray ray);
}
