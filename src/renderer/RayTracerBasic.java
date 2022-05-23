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
        GeoPoint geoPoint = findClosestIntersection(ray);
        return calcColor(geoPoint,ray);
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
        return calcColor(geoPoint,ray,MAX_CALC_COLOR_LEVEL,INITIAL_K).add(scene._ambientLight.getIntensity());
    }

    /**
     *
     * @param intersection
     * @param ray
     * @param level
     * @param k
     * @return
     */
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
        Ray lightRay = new Ray(geoPoint, lightDirection,n);///////
        double distance=light.getDistance(geoPoint);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,distance);
        if(intersections==null)
            return true;

        for(GeoPoint gp:intersections){

            if(gp.point.distance(geopoint.point)<distance && gp.geometry.getMaterial()._kT.equals(new Double3(0.0)))
            {
                return false;
            }

        }
        return true;
    }




    /**
     * function that calculate the color in a point with the effect on the point
     * @param gp GeoPoint
     * @param ray- ray from the camera
     * @return
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray,Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir ();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;
        color=Color.BLACK;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr=transparency(gp,lightSource,l,n);
                if(!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }



    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n){
        Vector lightDirection = l.scale(-1); // from point to light source
        double nl=n.dotProduct(lightDirection);
        Vector epsVector;
        if(nl>0) {
            epsVector = n.scale(EPS);
        }
        else
            epsVector = n.scale(-EPS);

        Point gp = geoPoint.point.add(epsVector);
        Ray lightRay = new Ray(gp, lightDirection);
        double distance= ls.getDistance(gp);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,distance);
        if(intersections==null){
            return Double3.ONE;
        }
        Double3 ktr= Double3.ONE;
        for(GeoPoint geoP:intersections) {
            {
                ktr = ktr.product(geoP.geometry.getMaterial()._kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                    return ZERO;
                }
            }
        }
        return ktr;
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

        if (isZero(nl)) {
            throw new IllegalArgumentException("nl cannot be Zero for scaling the normal vector");
        }
        Vector r = l.subtract(n.scale(2 * nl));
        double vr = alignZero(v.dotProduct(r));
        if (vr >= 0) {
            return ZERO;
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
        if(isZero(alignZero(n.dotProduct(ray.getDir())))){
            return intersection.geometry.getEmission();
        }
       if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
           color = color.add(calcSecondaryRayColor(reflectedRay,level,kkr)
                .scale(intersection.geometry.getMaterial()._kR));
       }
        Ray refractedRay = constructRefractedRay(intersection.point, ray,n);
        GeoPoint refractedPoint = findClosestIntersection(refractedRay);
        Double3 kkt=intersection.geometry.getMaterial()._kT.product(k);
       if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
           color = color.add(calcSecondaryRayColor(refractedRay,level,kkt)
                   .scale(intersection.geometry.getMaterial()._kT));
       }
        return color;
    }

    /**
     *
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcSecondaryRayColor(Ray ray,int level,Double3 k){
        GeoPoint geoPoint=findClosestIntersection(ray);
        Color color=scene._background;
        if(geoPoint!=null){
            color=calcColor(geoPoint,ray,level-1,k);
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
