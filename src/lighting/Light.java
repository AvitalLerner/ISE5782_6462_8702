package lighting;

import primitives.Color;

/**
 * abstract class to describe light and color of the light
 * the classes that inherit it implement the methods according to the type of light
 */
public abstract class Light {
    /**
     * the color of the light
     */
    protected Color _intensity;

    /**
     * constructor
     * @param intensity the color of the light
     */
    protected Light(Color intensity)
    {
        this._intensity = intensity;
    }

    /**
     * getter of _intensity
     * @return _intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
