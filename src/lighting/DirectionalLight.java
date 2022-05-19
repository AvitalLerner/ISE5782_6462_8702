package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    private Vector direction;

    /**
     * constructor
     * @param intensity the color of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * getter of intensity
     * @param p
     * @return intensity
     */
    @Override
    public Color getIntensity(Point p) {return _intensity;}

    /**
     * getter of the direction of the light
     * @param p
     * @return direction
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }

    /**
     * getter of the distance of the light
     * @param p
     * @return
     */
    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }
}
