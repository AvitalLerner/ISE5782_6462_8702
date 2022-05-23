package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * common interface for all 3D objects
 * that intersect with a specific Ray{@link primitives.Ray}
 */
abstract public class Intersectable {
    /**
     * find the closest intersection with the ray
     * @param r ray
     * @param distance
     * @return
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray r,double distance);

    /**
     * find all intersection points {@link Point}
     * that intersect the shape from a specific Ray{@link primitives.Ray}
     *
     * @param ray Ray pointing towards the graphic object
     * @return immutable list of intersection points
     */
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersection(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * find the intersection of GeoPoint with ray "r"
     * @param r ray
     * @return list of GeoPoint intersection
     */
    public final List<GeoPoint> findGeoIntersection(Ray r)
    {
        return findGeoIntersections(r,Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray r, double distance)
    {
        return findGeoIntersectionsHelper(r,distance);
    }


    /**
     * class GeoPoint
     */
    public static class GeoPoint {
        /**
         * geometry of GeoPoint
         */
        public  Geometry geometry;

        /**
         * point of GeoPoint
         */
        public Point point;

        /**
         * constructor get geometry and point
         * @param geometry of the GeoPoint
         * @param point of the GeoPoint
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * @return string of the data of GeoPoint
         */
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

        /**
         * check if object equal to this GeoPoint
         * @param obj object
         * @return if the object equal to this GeoPoint
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) obj;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }
    }
}
