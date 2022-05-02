package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {
    protected Color _emission=Color.BLACK;
    private Material _material=new Material();
    /**
     * calculate the normal from the point
     * @param p1 point to calculate the normal
     * @return normal
     */
    public abstract Vector getNormal(Point p1);

    /**
     *
     * @return
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     *
     * @param emission
     * @return
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     *
     * @return
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     *
     * @param material
     */
    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }
}
