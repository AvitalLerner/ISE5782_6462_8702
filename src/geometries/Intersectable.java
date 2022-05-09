package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * common interface for all 3D objects
 * that intersect with a specific Ray{@link primitives.Ray}
 */
public abstract class Intersectable {
    /**
     * find all intersection points {@link Point}
     * that intersect the shape from a specific Ray{@link primitives.Ray}
     *
     * @param ray Ray pointing towards the graphic object
     * @return immutable list of intersection points
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersection(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * @param r
     * @return
     */
    public List<GeoPoint> findGeoIntersection(Ray r) {
        return findGeoIntersections(r,Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray r, double distance) {
        return findGeoIntersectionsHelper(r,distance);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray r,double distance);

    /**
     *
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

    }

}
