package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracer{
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        Color result = scene._background;
        List<Point> allPoints = scene.geometries.findIntersections(ray);
        if(allPoints != null){
            Point pt = ray.findClosestPoint(allPoints);
            result = calcColor(pt);
        }
        return result;
    }

    /**
     * calculate the color in point
     * @param point in the scene
     * @return the color in the point
     */
    private Color calcColor(Point point) {

        return scene._ambientLight.getIntensity();

    }
}
