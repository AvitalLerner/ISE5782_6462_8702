package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * interface of light sources of the scene
 */
public interface LightSource {
    /**
     * get of the intensity of the light in point p
     * @param p point in the scene
     * @return the intensity of the light
     */
    public Color getIntensity(Point p);

    /**
     * l is the vector between the light source and point p
     * @param p point in the scene
     * @return l
     */
    public Vector getL(Point p);

    /**
     * calculate the distance between the light source and point p
     * @param p point in the scene
     * @return the distance
     */
    public double getDistance(Point p);
    public List<Vector> circleBeam(Point p,double radius,int numRays);
    
    public String getType();
}