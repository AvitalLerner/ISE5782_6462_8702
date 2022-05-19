package lighting;

import primitives.Color;

public abstract class Light {
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
