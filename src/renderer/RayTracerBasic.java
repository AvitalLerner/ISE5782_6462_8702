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
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     *
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
     * @param l
     * @param n
     * @param geopoint
     * @return
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Point pointGeo = geopoint.point;
        Ray lightRay = new Ray(pointGeo, lightDirection, n);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersection(lightRay);
        if (intersections == null) {
            return true;
        }
        double lightDistance = light.getDistance(pointGeo);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(pointGeo) - lightDistance) <= 0
                    && gp.geometry.getMaterial().kT.equals(0) )
            {
                return false;
            }
        }
        return true;
    }

    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir ();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
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
        return material.getkS().scale(Math.pow(-1d * vr,material.getShininess()));}

    private Double3 calcDiffusive(Material material,double nl) {
        double abs_nl = Math.abs(nl);
        return  material._kD.scale(abs_nl);
    }

    }
