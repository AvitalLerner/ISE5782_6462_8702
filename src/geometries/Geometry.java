package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {
    private Material _material = new Material();
    private Color _emission = Color.BLACK;

    /**
     * calculate the normal from the point
     *
     * @param p1 point to calculate the normal
     * @return normal
     */
    public abstract Vector getNormal(Point p1);

    /**
     * getter for emission
     * @return emission
     */
    /**
     * @param emission
     * @return
     */

    /**
     * getter for material
     *
     * @return material
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * settter for material
     *
     * @param material
     */
    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    public Color getEmission() {
        return _emission;
    }

    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }
}
