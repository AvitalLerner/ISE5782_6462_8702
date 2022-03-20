package geometries;

import primitives.Point;
import primitives.Vector;

public interface Geometry extends Intersectable {
    /**
     * calculate the normal from the point
     * @param p1 point to calculate the normal
     * @return normal
     */
    public Vector getNormal(Point p1);
}
