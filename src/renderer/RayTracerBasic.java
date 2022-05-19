package renderer;

import geometries.Geometry;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Double3.ZERO;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class RayTracerBasic extends RayTracer {
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * calculate the color
     * @param ray
     * @return
     */
    @Override
    public Color traceRay(Ray ray) {
        Color result = scene._background;
        List<GeoPoint> allPoints = scene.geometries.findGeoIntersection(ray);
        if (allPoints != null) {
            GeoPoint pt = ray.findClosestGeoPoint(allPoints);
            result = calcColor(pt,ray);
        }
        return result;
    }

    /**
     * calculate the color in point
     * @param geoPoint in the scene
     * @return the color in the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene._ambientLight.getIntensity()//
                .add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * function that check if point shading by the light source
     * and if there is something that cover the light
     * @param light the lightSource
     * @param l vector of light Direction
     * @param n vector of
     * @param geopoint point to check if it's shading
     * @return
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        double nl=n.dotProduct(lightDirection);
        Point geoPoint = geopoint.point;
        Ray lightRay = new Ray(geoPoint, lightDirection, n);
        double distance=light.getDistance(geoPoint);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,distance);
        return intersections==null;
    }

    /**
     * function that calculate the color in a point with the effect on the point
     * @param gp GeoPoint
     * @param ray- ray from the camera
     * @return
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir ();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if(unshaded(lightSource,l,n,gp)) {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     *
     * @param material
     * @param n
     * @param l
     * @param nl
     * @param v
     * @return
     */
    private Double3 calcSpecular(Material material,Vector n,Vector l,double nl,Vector v) {
        // nl is the dot product among the vector from the specular light to the point and the normal vector of the point
        //nl must not be zero
        if (isZero(nl)) {
            throw new IllegalArgumentException("nl cannot be Zero for scaling the normal vector");
        }
        Vector r = l.subtract(n.scale(2 * nl));
        double vr = alignZero(v.dotProduct(r));
        if (vr >= 0) {
            return ZERO; // view from direction opposite to r vector
        }
        return material.getkS().scale(Math.pow(-1d * vr,material.getShininess()));
    }

    /**
     *
     * @param material
     * @param nl
     * @return
     */
    private Double3 calcDiffusive(Material material,double nl) {
        double abs_nl = Math.abs(nl);
        return  material._kD.scale(abs_nl);
    }

    private Ray constructReflectedRay(Point pointGeo, Ray ray, Vector n) {
        Vector v = ray.getDir();
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
    }
    /**
     *
     * @param intersection
     * @param ray
     * @return
     */
    private Color calcGlobalEffects(GeoPoint intersection, Ray ray) {
        Color color = Color.BLACK;
        Vector n =intersection.geometry.getNormal(intersection.point);
        Ray reflectedRay = constructReflectedRay(intersection.point, ray,n);
        GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
    //    if ()
        color = color.add(calcColor(reflectedPoint, reflectedRay)
                .scale(intersection.geometry.getMaterial().kR));
        Ray refractedRay = constructRefractedRay(intersection.point, ray);
        GeoPoint refractedPoint = findClosestIntersection(refractedRay);
       // if (…)
        color = color.add(calcColor(refractedPoint, refractedRay)
                .scale(intersection.geometry.getMaterial().kT));
        return color;
    }

    private Ray constructRefractedRay(Point point, Ray ray) {
        return null;
    }

    private GeoPoint findClosestIntersection(Ray reflectedRay) {
        return null;
    }
}
