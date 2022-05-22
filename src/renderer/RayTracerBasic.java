package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Double3.ZERO;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class RayTracerBasic extends RayTracer {
    private static final double EPS = 0.1;
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;
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
        if (geoPoint==null){
            return scene._background;
        }
        return calcColor(geoPoint,ray,MAX_CALC_COLOR_LEVEL,INITIAL_K);
    }

    private Color calcColor(GeoPoint intersection, Ray ray,int level,Double3 k){
        Color color = intersection.geometry.getEmission()
                .add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
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
        Vector epsVector;
        if(nl>0) {
            epsVector = n.scale(EPS);
        }
        else
            epsVector = n.scale(-EPS);

        Point geoPoint = geopoint.point.add(epsVector);
        Ray lightRay = new Ray(geoPoint, lightDirection);
        //double distance=light.getDistance(geoPoint);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersection(lightRay);
        return intersections==null;
    }

    /**
     * function that calculate the color in a point with the effect on the point
     * @param gp GeoPoint
     * @param ray- ray from the camera
     * @return
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray,double k) {
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
                double ktr=transparency(l,n,gp);
                if(ktr*k>MIN_CALC_COLOR_K) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    private double transparency(Vector l, Vector n, GeoPoint gp) {

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
        return material.get_kS().scale(Math.pow(-1d * vr,material.getShininess()));
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
    private Color calcGlobalEffects(GeoPoint intersection, Ray ray,int level,Double3 k) {
        Color color = Color.BLACK;
        Vector n =intersection.geometry.getNormal(intersection.point);
        Ray reflectedRay = constructReflectedRay(intersection.point, ray,n);
        GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
        Double3 kkr=intersection.geometry.getMaterial()._kR.product(k);
       if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
           color = color.add(calcColor(reflectedPoint, reflectedRay,level-1,kkr)
                .scale(intersection.geometry.getMaterial()._kR));
       }
        Ray refractedRay = constructRefractedRay(intersection.point, ray,n);
        GeoPoint refractedPoint = findClosestIntersection(refractedRay);
        Double3 kkt=intersection.geometry.getMaterial()._kT.product(k);
       if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
           color = color.add(calcColor(refractedPoint, refractedRay,level-1,kkt)
                   .scale(intersection.geometry.getMaterial()._kT));
       }
        return color;
    }

    private Ray constructRefractedRay(Point point, Ray ray,Vector v) {
        return new Ray(point,ray.getDir(),v);
    }

    private GeoPoint findClosestIntersection(Ray reflectedRay) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersection(reflectedRay);
        if (intersections == null){
            return null;
        }
       return reflectedRay.findClosestGeoPoint(intersections);
    }
}
