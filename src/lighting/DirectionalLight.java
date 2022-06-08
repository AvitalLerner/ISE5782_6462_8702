package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * class directionLight to show source light that have direction
 * the class calculate the intensity, direction and distance of the light
 */
public class DirectionalLight extends Light implements LightSource {
    /**
     * direction of the light
     */
    private Vector direction;
    private double _angle;
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

    @Override
    public List<Vector> circleBeam(Point p, int numRays) {
        return List.of(this.direction.normalize());
    }

    @Override
    public String getType() {
        return "DirectionalLight";
    }
}
