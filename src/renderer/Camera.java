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

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i)
    {
        double Rx = _width / nX;
        double Ry = _height / nY;

        double xJ = (j -(nX-1)/2)*Rx;
        double yI = i -(nY-1)/2*Ry;

        Point pIJ=_p0.add(_vTo.scale(_distance));

        if (isZero(xJ) && isZero(yI)) {
            return new Ray(_p0, pIJ.subtract(_p0));
        }
        else {
            if (isZero(xJ))
                pIJ = pIJ.add(_vUp.scale(yI));

            if (isZero(yI))
                pIJ = pIJ.add(_vRight.scale(xJ));


            if (!isZero(yI) && !isZero(xJ))
                pIJ = pIJ.add(_vUp.scale(yI)).add(_vRight.scale(xJ));
        }

        return new Ray(_p0, pIJ.subtract(_p0));

        //  Ray result = pIJ + _vRight.scale(xJ)-yI*_height;


    }
}
