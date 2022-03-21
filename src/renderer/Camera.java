package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    private Point _p0;
    private Vector _vUp;
    private Vector _vTo;
    private Vector _vRight;

    private double _distance;
    private double _width;
    private double _height;

    public Camera(Point p0, Vector vUp, Vector vTo) {
        _p0 = p0;
        if( !isZero(vUp.dotProduct(vTo))){
            throw new IllegalArgumentException("vup and vto are not orthogonal");
        }
        _vUp = vUp.normalize();
        _vTo = vTo.normalize();

        _vRight = _vTo.crossProduct(_vUp);
    }

    public Camera setVPSize(double width, double height){
        _width = width;
        _height = height;
        return this;
    }

    public Camera setVPDistance(double distance){
        _distance = distance;
        return this;
    }

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {return null;}
}
