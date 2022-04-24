package renderer;

import primitives.Color;
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

    private ImageWriter _writer;
    private  RayTracerBase _tracerBase;
    public Camera setWriter(ImageWriter writer) {
        _writer = writer;
        return this;
    }

    public Camera setTracerBase(RayTracerBase tracerBase) {
        _tracerBase = tracerBase;
        return this;
    }



    public Camera setWidth(double width) {
        _width = width;
        return this;
    }

    public Camera setHeight(double height) {
        _height = height;
        return this;
    }

    public Camera(Point p0, Vector vTo, Vector vUp) {
        _p0 = p0;
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("vup and vto are not orthogonal");
        }
        _vUp = vUp.normalize();
        _vTo = vTo.normalize();

        _vRight = _vTo.crossProduct(_vUp);
    }

    public Camera setVPSize(double width, double height) {
        _width = width;
        _height = height;
        return this;
    }

    public Camera setVPDistance(double distance) {
        _distance = distance;
        return this;
    }

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        double Rx = _width / nX;
        double Ry = _height / nY;

        double xJ = (j - (nX - 1) / 2d) * Rx;
        double yI = -(i - (nY - 1) / 2d) * Ry;

        Point pIJ = _p0.add(_vTo.scale(_distance));

        if (isZero(xJ) && isZero(yI)) {
            return new Ray(_p0, pIJ.subtract(_p0));
        } else {
            if (isZero(xJ))
                pIJ = pIJ.add(_vUp.scale(yI));

            if (isZero(yI))
                pIJ = pIJ.add(_vRight.scale(xJ));


            if (!isZero(yI) && !isZero(xJ))
                pIJ = pIJ.add(_vRight.scale(xJ)).add(_vUp.scale(yI));
        }

        return new Ray(_p0, pIJ.subtract(_p0));


    }

    public Camera BuilderCamera(Point p,Vector vTo, Vector vUp){

        return null;
    }
    public Camera setImageWriter(ImageWriter base_render_test) {
        return this;
    }

    public void renderImage() {
         if(_p0==null||_vUp==null|| _vTo==null|| _vRight==null||
                _distance==0.0 || _width==0.0|| _height==0.0) {
            // throw new Exception("MissingResourcesException")

         }
        int nX = 800;
        int nY = 500;

        int interval = 50; // 800/50 == 16  500/50 == 10

        Color redColor = new Color(255d,0,0d);

        ImageWriter imageWriter = new ImageWriter("YellowSubmarine",nX,nY);
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                if(i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, redColor);
                }
            }
        }
        imageWriter.writeToImage();
    }

    public void printGrid(int i, Color color) {

    }

    public void writeToImage() {
      if (_writer==null){
          //throw new Exception("MissingResourcesException");
      }
    }

    public Camera setRayTracer(RayTracerBase rayTracerBasic) {
        return this;
    }

    public Camera build() {
        return this;
    }
}
