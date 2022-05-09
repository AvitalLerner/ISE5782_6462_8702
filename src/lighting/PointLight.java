package lighting;

import primitives.*;

public class PointLight extends Light implements LightSource{
    private Point _position;
    private Double3 _kC = Double3.ONE;
    private Double3 _kL = Double3.ZERO;
    private Double3 _kQ = Double3.ZERO;

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this._position = position;
    }
    @Override
    public Color getIntensity(Point p) {
        Color lightIntensity = getIntensity();
        double distanceSquared = p.distanceSquared(_position);
        double distance = p.distance(_position);

        Double3 kqdist =_kQ.scale(distanceSquared);
        Double3 kldist =_kL.scale(distance);

        Double3 denom  =(_kC.add(kldist)).add(kqdist);

        return lightIntensity.reduce(denom);
    }

    @Override
    public Vector getL(Point p) {
      Vector l=_position.subtract(p).normalize();
      return l;
    }

    @Override
    public double getDistance(Point p) {
        return _position.distance(p);
    }

    public PointLight setkC(double kC) {
        this._kC =  new Double3(kC);
        return this;
    }

    public PointLight setKl(double kL) {
        this._kL = new Double3(kL) ;
        return this;
    }

    public PointLight setKq(double kQ) {
        this._kQ =  new Double3(kQ);
        return this;
    }
   public PointLight setkC(Double3 kC) {
        this._kC = kC;
        return this;
    }

    public PointLight setKl(Double3 kL) {
        this._kL = kL;
        return this;
    }

    public PointLight setKq(Double3 kQ) {
        this._kQ = kQ;
        return this;
    }

}
