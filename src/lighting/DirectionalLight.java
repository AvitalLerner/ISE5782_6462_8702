package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class directionLight to show source light that have direction
 * the class calculate the intensity, direction and distance of the light
 */
public class DirectionalLight extends Light implements LightSource{
    /**
     * direction of the light
     */
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

    @Override
    public Color getIntensity(Point p) {return _intensity;}


    @Override
    public Vector getL(Point p) {
        return direction;
    }


    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }
}
