package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    private RayTracer _tracerBase;

    public Camera setWriter(ImageWriter writer) {
        _writer = writer;
        return this;
    }

    public Camera setRayTracer(RayTracerBasic tracerBase) {
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
    public Camera setImageWriter(ImageWriter imageWriter) {
        _writer= imageWriter;
        return this;
    }

    public void renderImage() {
         if(_p0==null||_vUp==null|| _vTo==null|| _vRight==null||
                _distance==0.0 || _width==0.0|| _height==0.0) {
            // throw new Exception("MissingResourcesException")

         }

        int interval = 50; // 800/50 == 16  500/50 == 10


        int Ny = _writer.getNy();
        int Nx = _writer.getNx();

        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                   castRay(Ny,Nx,i,j);
                }
            }
        }

    private void castRay(int ny, int nx, int i, int j) {
        Ray ray = constructRayThroughPixel(nx,ny,j,i);
        Color pixelColor = _tracerBase.traceRay(ray);
        _writer.writePixel(j,i,pixelColor);
    }

    public void printGrid(int interval, Color color) {
        _writer.printGrid(interval,color);
    }

    public void writeToImage() {
      _writer.writeToImage();
    }

    public Camera build() {
        return this;
    }
}
