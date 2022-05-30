package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Double3.ZERO;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class RayTracerBasic extends by abstract class RayTracer
 */
public class RayTracerBasic extends RayTracer {
    private static final double EPS = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * constructor
     * @param scene the scene of the picture
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    @Override
    public Color traceRay(Ray ray) {
        Color result = scene._background;
        List<GeoPoint> allPoints = scene.geometries.findGeoIntersection(ray);
        if (allPoints != null) {
            GeoPoint pt = ray.findClosestGeoPoint(allPoints);
            result = calcColor(pt, ray);
        }
        return result;
    }

    /**
     * calculate the color in point
     *
     * @param geoPoint in the scene
     * @return the color in the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        if (geoPoint == null) {
            return scene._background;
        }
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K);
    }

    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = intersection.geometry.getEmission()
                .add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    private Color calcSecondaryRayColor(Ray ray, int level, Double3 k) {
        GeoPoint geoPoint = findClosestIntersection(ray);
        Color color = scene._background;
        if (geoPoint != null) {
            color = calcColor(geoPoint, ray, level - 1, k);
        }
        return color;
    }

    /**
     * function that check if point shading by the light source
     * and if there is something that cover the light
     *
     * @param light    the lightSource
     * @param l        vector of light Direction
     * @param n        vector of
     * @param geopoint point to check if it's shading
     * @return
     */

    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        double nl = n.dotProduct(lightDirection);
        Vector epsVector;
        if (nl > 0) {
            epsVector = n.scale(EPS);
        } else
            epsVector = n.scale(-EPS);

        Point geoPoint = geopoint.point.add(epsVector);
        Ray lightRay = new Ray(geoPoint, lightDirection);
        double distance = light.getDistance(geoPoint);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersection(lightRay);
        if (intersections == null)
            return true;

        for (GeoPoint gp : intersections) {

            if (gp.point.distance(geopoint.point) < distance && gp.geometry.getMaterial().kT.equals(new Double3(0.0))) {
                return false;
            }

        }
        return true;
    }


    /**
     * function that calculate the color in a point with the effect on the point
     *
     * @param gp   GeoPoint
     * @param ray- ray from the camera
     * @return
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * calculate transparency
     * @param geoPoint
     * @param ls
     * @param l
     * @param n
     * @return
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double distance = ls.getDistance(geoPoint.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersection(lightRay);
        if (intersections == null) {
            return Double3.ONE;
        }

        intersections.removeIf((gp) -> {
            double gpdistance = gp.point.distance(geoPoint.point);
            return gpdistance > distance;
        });

        Double3 ktr = Double3.ONE;
        for (GeoPoint geoP : intersections) {
            ktr = ktr.product(geoP.geometry.getMaterial().kT);
            if(ktr.lowerThan(MIN_CALC_COLOR_K)){
                return Double3.ZERO;
            }
        }

        return ktr;
    }

    /**
     * calculate specular
     * @param material
     * @param n
     * @param l
     * @param nl
     * @param v
     * @return
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
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
        return material.kS.scale(Math.pow(-1d * vr, material.getShininess()));
    }

    /**
     * @param material
     * @param nl
     * @return
     */
    private Double3 calcDiffusive(Material material, double nl) {
        double abs_nl = Math.abs(nl);
        return material.kD.scale(abs_nl);
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
     * @param intersection
     * @param ray
     * @return
     */
    private Color calcGlobalEffects(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = intersection.geometry.getNormal(intersection.point);
        Ray reflectedRay = constructReflectedRay(intersection.point, ray, n);
        GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
        Double3 kkr = intersection.geometry.getMaterial().kR.product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcSecondaryRayColor(reflectedRay, level, kkr)
                    .scale(intersection.geometry.getMaterial().kR));
        }
        Ray refractedRay = constructRefractedRay(intersection.point, ray, n);
        GeoPoint refractedPoint = findClosestIntersection(refractedRay);
        Double3 kkt = intersection.geometry.getMaterial().kT.product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcSecondaryRayColor(refractedRay, level, kkt)
                    .scale(intersection.geometry.getMaterial().kT));
        }
        return color;
    }

    private Ray constructRefractedRay(Point point, Ray ray, Vector v) {
        return new Ray(point, ray.getDir(), v);
    }

    /**
     * Calculate the GeoPoint of intersection closest to the start of the ray
     * @param reflectedRay rat of reflection
     * @return intersection closest to the start of the ray
     */
    private GeoPoint findClosestIntersection(Ray reflectedRay) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersection(reflectedRay);
        if (intersections == null) {
            return null;
        }
        return reflectedRay.findClosestGeoPoint(intersections);
    }
}
