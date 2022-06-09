package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class of spotlight,
 * the light has place in the scene, color, reduction factor of light intensity and direction
 * the class calculate the intensity, direction and distance of the light
 */
public class SpotLight extends PointLight {
    /**
     * direction of the spotLight
     */
    private Vector _direction;
    /**
     * constructor of spotLight
     * @param intensity the color of the light
     * @param position the place of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction,double angle) {
        super(intensity, position);
        this._direction = direction.normalize();
        this._angle=angle;
    }
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this._direction = direction.normalize();
        this._angle=60;
    }

    @Override
    public Color getIntensity(Point p) {
        Color pointLight = super.getIntensity(p);
        double dirL = Math.max( 0,_direction.dotProduct(getL(p)));
        return  pointLight.scale(dirL);
    }
    @Override
    public String getType() {
        return "SpotLight";
    }
}
