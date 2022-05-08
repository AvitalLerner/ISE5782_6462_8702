package lighting;

import primitives.Color;

public abstract class Light {
    protected Color _intensity;

    protected Light(Color intensity) {
        this._intensity = intensity;
    }

    public Color getIntensity() {
        return _intensity;
    }
}
