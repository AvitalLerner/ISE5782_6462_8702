package lighting;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * class point light to show light source.
 * the light has place in the scene, color and reduction factor of light intensity
 * the class calculate the intensity, direction and distance of the light
 */
public class PointLight extends Light implements LightSource {
    /**
     * the place of the light in the scene
     */
    private Point _position;
    /**
     * the angle of the light rays
     */
    public double _angle=180;
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

    @Override
    public List<Vector> circleBeam(Point p, int numRays) {
    Random r = new Random();
    double distance=p.distance(_position);
    double tan_a=Math.tan(_angle);
    double radius=distance/tan_a;
    List<Vector> vectors = new LinkedList();
        for (double i = -radius; i < radius; i += radius / 10) {
        for (double j = -radius; j < radius; j += radius / 10) {
            if (i != 0 && j != 0) {
                Point point = _position.add(new Vector(i, 0.1d, j));

                if (point.equals(_position)){
                    vectors.add(p.subtract(point).normalize());
                }
                else{
                    try{
                        if (point.subtract(_position).dotProduct(point.subtract(_position)) <= radius * radius){
                            vectors.add(p.subtract(point).normalize());
                        }
                    }
                    catch (Exception e){
                        vectors.add(p.subtract(point).normalize());
                    }

                }
            }

        }
    }
        vectors.add(getL(p));
        return vectors;
}


    @Override
    public String getType() {
        return "PointLight";
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
