package geometries;

import primitives.Point;
import primitives.Vector;

public interface Geometry extends Intersectable {
    /**
     *
     * @param p1
     * @return
     */
    public Vector getNormal(Point p1);
}
