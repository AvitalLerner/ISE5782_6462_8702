package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point _position;
    private double _kC;
    private double _kL;
    private double _kQ;

    public PointLight(Color intensity, Point position, double kC, double kL, double kQ) {
        super(intensity);
        this._position = position;
        _kC = kC;
        _kL = kL;
        _kQ = kQ;
    }
    public PointLight(Color colorIntensity, Point position) {
        this(colorIntensity, position, 1d, 0d, 0d);
    }
    @Override
    public Color getIntensity(Point p) {
        return null;
    }

    @Override
    public Vector getL(Point p) {
        return null;
    }

    public PointLight setkC(double kC) {
        this._kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this._kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this._kQ = kQ;
        return this;
    }
}
