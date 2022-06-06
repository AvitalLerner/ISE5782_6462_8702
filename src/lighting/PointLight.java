package lighting;

import primitives.*;

/**
 * class point light to show light source.
 * the light has place in the scene, color and reduction factor of light intensity
 * the class calculate the intensity, direction and distance of the light
 */
public class PointLight extends Light implements LightSource{
    /**
     * the place of the light in the scene
     */
    private Point _position;

    /**
     *
     */
    private Double3 _kC = Double3.ONE;
    private Double3 _kL = Double3.ZERO;
    private Double3 _kQ = Double3.ZERO;

    /**
     * constructor
     * @param intensity the color of the light
     * @param position the place of the light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this._position = position;
        _kC=new Double3(1);
        _kL=new Double3(0);
        _kQ=new Double3(0);
    }

    @Override
    public Color getIntensity(Point p) {
        Color lightIntensity = getIntensity();
        double distanceSquared = _position.distanceSquared(p);
        double distance = _position.distance(p);

        Double3 kqdist =_kQ.scale(distanceSquared);
        Double3 kldist =_kL.scale(distance);

        Double3 denom  =(_kC.add(kldist)).add(kqdist);

        return lightIntensity.reduce(denom);
    }

    @Override
    public Vector getL(Point p) {
       if(_position.equals(p)){
           return null;
       }
        Vector l = p.subtract(_position).normalize();
        return l;
    }

    @Override
    public double getDistance(Point p) {
        return p.distance(_position);
    }

    //setters of the parameters

    /**
     * setter of kC
     * @param kC of type double
     * @return this
     */
    public PointLight setKc(double kC) {
        this._kC =  new Double3(kC);
        return this;
    }

    /**
     * setter of kL
     * @param kL of type double
     * @return this
     */
    public PointLight setKl(double kL) {
        this._kL = new Double3(kL) ;
        return this;
    }

    /**
     * setter of kQ
     * @param kQ of type double
     * @return this
     */
    public PointLight setKq(double kQ) {
        this._kQ =  new Double3(kQ);
        return this;
    }

    /**
     * setter of kC
     * @param kC of type Double3
     * @return this
     */
   public PointLight setKc(Double3 kC) {
        this._kC = kC;
        return this;
    }

    /**
     * setter of kL
     * @param kL of type Double3
     * @return this
     */
    public PointLight setKl(Double3 kL) {
        this._kL = kL;
        return this;
    }

    /**
     * setter of kQ
     * @param kQ of type Double3
     * @return this
     */
    public PointLight setKq(Double3 kQ) {
        this._kQ = kQ;
        return this;
    }

}
