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
    public List<Vector> circleBeam(Point p, double radius, int numRays) {
        double x,y,cos,sin;
        Random rand=new Random();
        Point randPoint;
        Vector v=getL(p);
        List<Vector> beam=new LinkedList<>();
        if(_position.equals(p)){
            return null;
        }
        beam.add(p.subtract(_position).normalize());
        if(numRays<=1) {
            return beam;
        }
        Vector normalX;

        // if v = (0,0,z) then the normal of x is (-z,0,0)
        // else the normal of x is (-y,x,0)
        if (v.getX()== 0 && v.getY() == 0) {
            normalX = new Vector(v.getZ() * -1, 0, 0).normalize();
        }
        else {
            normalX = new Vector(v.getY() * -1, v.getX(), 0).normalize();
        }

        // the normal for y will be the cross product between v and the normal of x
        Vector normalY = v.crossProduct(normalX).normalize();

        for(int i=0;i<numRays;i++){
            randPoint=_position;
            cos=2*rand.nextDouble()-1;
            sin=Math.sqrt(1d-cos*cos);
            double circleRange=radius*(2*rand.nextDouble()-1);
            //if point is original point light intersection then skip
            if (circleRange==0){
                i--;
                continue;
            }
            x=circleRange*cos;
            y=circleRange*sin;


            if (x != 0) {
                randPoint = randPoint.add(normalX.scale(x));
            }

            if (y != 0) {
                randPoint = randPoint.add(normalY.scale(y));
            }

            beam.add(p.subtract(randPoint).normalize());

        }
        return beam;
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
