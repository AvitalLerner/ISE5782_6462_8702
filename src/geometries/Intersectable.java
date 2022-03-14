package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * common interface for all 3D objects
 * that intersect with a specific Ray{@link primitives.Ray}
 */
public interface Intersectable {
    /**
     * find all intersection points {@link Point}
     * that intersect the shape from a specific Ray{@link primitives.Ray}
     * @param ray Ray pointing towards the graphic object
     * @return immutable list of intersection points
     */
    public List<Point> findIntersections(Ray ray);
}
