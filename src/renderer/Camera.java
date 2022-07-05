package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Polygon;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.MissingResourceException;
import static primitives.Util.isZero;


public class Camera {

    /**
     * the place of the camera
     */
    private Point _p0;
    /**
     * vector up of the camera
     */
    private Vector _vUp;
    /**
     * vector to the view plane
     */
    private Vector _vTo;
    /**
     * vector right of the camera
     */
    private Vector _vRight;
    /**
     * the distance between the camera and the view plane
     */
    private double _distance;
    /**
     * the width of the view plane
     */
    private double _width;
    /**
     * the height of the view plane
     */
    private double _height;

    private ImageWriter _writer;
    private RayTracer _rayTracer;
    private Aperture _aperture;

    /**
     * constructor get point and 2 vectors
     * @param p0 of the location of the camera
     * @param vTo vector to the view plane
     * @param vUp vector to up
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        _p0 = p0;
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("vup and vto are not orthogonal");
        }
        _vUp = vUp.normalize();
        _vTo = vTo.normalize();
        _vRight = _vTo.crossProduct(_vUp);
        Point focalPlane=null;
        setAperture(focalPlane);// ברירת מחדל של נקודה
    }

    public Camera(Point p0, Vector vTo, Vector vUp,Point focalPoint) {
        _p0 = p0;
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("vup and vto are not orthogonal");
        }
        _vUp = vUp.normalize();
        _vTo = vTo.normalize();
        _vRight = _vTo.crossProduct(_vUp);
        //setAperture(focalPoint);
    }

    public Camera setAperture(Point focal) {
        double width=_width/2;
        double height=_height/2;
        double distance=_distance/2;
        if (focal==null){
            focal=new Point(_p0.getX(),_p0.getY(), _p0.getZ()-((_distance)/3)*2);
        }
       this._aperture=new Aperture(distance,width,height,focal);
       return this;
    }

    /**
     * setter of the distance between the camera end view plane
     * @param distance of type double
     * @return this
     */
    public Camera setVPDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * setter width
     * @param width of type double
     * @return this
     */
    public Camera setWidth(double width) {
        _width = width;
        return this;
    }

    /**
     * setter height
     * @param height of type double
     * @return this
     */
    public Camera setHeight(double height) {
        _height = height;
        return this;
    }
    /**
     * setter of writer
     * @param writer of type ImageWriter
     * @return this
     */
    public Camera setWriter(ImageWriter writer) {
        _writer = writer;
        return this;
    }

    /**
     * setter of writer
     * @param imageWriter of type ImageWriter
     * @return this
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        _writer = imageWriter;
        return this;
    }
    /**
     * setter of tracerBase
     * @param tracerBase
     * @return this
     */
    public Camera setRayTracer(RayTracerBasic tracerBase) {
        _rayTracer = tracerBase;
        return this;
    }

    /**
     * setter the data of view plane
     * @param width of view plane
     * @param height of view plane
     * @return this
     */
    public Camera setVPSize(double width, double height) {
        _width = width;
        _height = height;
        return this;
    }
    public Color constructRayThroughAperture(Ray ray){
        Point focalPlaneMid=_aperture._focalPlane;
        Color pixelColor=_rayTracer.traceRay(ray);
        Plane viewPlane=new Plane(new Point(_p0.getX(),_p0.getY(),_p0.getZ()-_distance),_vTo);
        Intersectable.GeoPoint focalPoint=viewPlane.findGeoIntersection(ray).get(0);
        Ray newRay=new Ray(new Point(0,0,0),new Vector(1,0,0));
        Polygon focalPlane=new Polygon(new Point(focalPlaneMid.getX()-(_width/10),focalPlaneMid.getY()+(_height/10),_p0.getZ()-_aperture._distanceFromCamera),
                new Point(focalPlaneMid.getX()+(_width/10),focalPlaneMid.getY()+(_height/10), _p0.getZ()-_aperture._distanceFromCamera),/// לא נכון הZ
                new Point(focalPlaneMid.getX()+(_width/10),focalPlaneMid.getY()-(_height/10),_p0.getZ()-_aperture._distanceFromCamera),
                new Point(focalPlaneMid.getX()-(_width/10),focalPlaneMid.getY()-(_height/10),_p0.getZ()-_aperture._distanceFromCamera));
        List<Intersectable.GeoPoint> intersection= focalPlane.findGeoIntersection(ray);
//        if (intersection==null){
//            return pixelColor;
//        }
        for(Point point:focalPlane._vertices){
            Vector v=focalPoint.point.subtract(point);
            ray=new Ray(focalPoint.point,v);
            pixelColor.add(_rayTracer.traceRay(ray));
        }
        //לבדוק האם הקרניים עוברות דרך הריבוע שניצור של הפוקל פליין אם לא עובר אז להחזיר רגיל ואם כן לעשות את השינויים הנצרכים
        // הפוקל פוינט נמצאת על הפליין
        return pixelColor;
    }
    /**
     * calculate the ray between the camera and pixel
     * @param nX num of pixels of the width of the view plane
     * @param nY num of pixels of the height of the view plane
     * @param j the location of the pixel on the width of the view plane
     * @param i the location of the pixel on the height of the view plane
     * @return the ray between the camera and pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        double Rx = _width / nX;
        double Ry = _height / nY;

        Point Pc = _p0.add(_vTo.scale(_distance));
        Point pIJ = Pc;

        double xJ = (j - (nX - 1) / 2d) * Rx;
        double yI = -(i - (nY - 1) / 2d) * Ry;

        if (isZero(xJ) && isZero(yI)) {
            return new Ray(_p0, pIJ.subtract(_p0));
        } else {
            if (!isZero(xJ))
                pIJ = pIJ.add(_vRight.scale(xJ));
            if (!isZero(yI))
                pIJ = pIJ.add(_vUp.scale(yI));

        }
        return new Ray(_p0, pIJ.subtract(_p0));
    }

    public Camera BuilderCamera(Point p, Vector vTo, Vector vUp) {
        return null;
    }


    /**
     * A function that colors the image
     * @return this
     */
    public Camera renderImage() {
        if (_p0 == null || _vUp == null || _vTo == null || _vRight == null ||
                _distance == 0.0 || _width == 0.0 || _height == 0.0) {
            throw new MissingResourceException("ERROR: some of the parameters don't correct", null, null);
        }

        int Ny = _writer.getNy();
        int Nx = _writer.getNx();

        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                castRay(Nx, Ny, i, j);
            }
        }
        _writer.writeToImage();
        return this;
    }

    /**
     * write pixel with the true color
     * Function castRay
     * @param nx num of pixels of the width of the view plane
     * @param ny num of pixels of the height of the view plane
     * @param j the location of the pixel on the width of the view plane
     * @param i the location of the pixel on the height of the view plane
     */
 //    private void castRay(int nx, int ny, int i, int j)
//    {
//        Ray ray = constructRayThroughPixel(nx, ny, j, i);
//        Color pixelColor = _rayTracer.traceRay(ray);
//        _writer.writePixel(j, i, pixelColor);
//    }

    /**
     * cast ray with super sampling 9*9 rays per pixel
     * @param nx num of pixels of the width of the view plane
     * @param ny num of pixels of the height of the view plane
     * @param j the location of the pixel on the width of the view plane
     * @param i the location of the pixel on the height of the view plane
     */
    private void castRay(int nx, int ny, int i, int j){
        int bigNy = 9*ny;
        int bigNx = 9*nx;
        Color pixelColor=new Color(java.awt.Color.BLACK);
        for (int iColumn = i*9; iColumn < i*9+9; iColumn++) {
            for (int jRow = j*9; jRow < j*9+9; jRow++) {
                Ray ray = constructRayThroughPixel(bigNx, bigNy, jRow, iColumn);// לא דרך הוי פליין אלה דרך הצמצם
                Color newColor=constructRayThroughAperture(ray);// sending to check if it goes through focal plane
                pixelColor=pixelColor.add(newColor) ;
            }
        }
        pixelColor=pixelColor.reduce(81);
        _writer.writePixel(j, i, pixelColor);
    }

    private Color helpFocalArea(Color pixelColor,int bigNx, int bigNy, int jRow, int iColumn) {
        int y=2*bigNy;
        int x =2*bigNx;
        Color pixelColor1=new Color(java.awt.Color.BLACK);
        for (int i = iColumn*2; i < iColumn*2+2; i++) {
            for (int j= jRow*2; j < jRow*2+2; j++) {
                Ray ray = constructRayThroughPixel(x, y, j, i);// לא דרך הוי פליין אלה דרך הצמצם
                pixelColor1 =pixelColor1.add(_rayTracer.traceRay(ray)) ;
            }
        }
        pixelColor1=pixelColor1.reduce(4);
        return pixelColor1;
    }

    private Ray constructRayThroughPixelFocus(int nX, int nY, int j, int i) {
        double Rx = _width / nX;
        double Ry = _height / nY;
        Point p=new Point(_aperture._focalPlane.getX(),_aperture._focalPlane.getY(),_p0.getZ()-_distance);
        Vector vTo=p.subtract(_aperture._focalPlane).normalize();
        Point Pc = _aperture._focalPlane.add(vTo.scale(_distance-_aperture._distanceFromCamera));
        Point pIJ = Pc;

        double xJ = (j - (nX - 1) / 2d) * Rx;
        double yI = -(i - (nY - 1) / 2d) * Ry;

        if (isZero(xJ) && isZero(yI)) {
            return new Ray(_aperture._focalPlane, pIJ.subtract(_aperture._focalPlane));
        } else {
            if (!isZero(xJ))
                pIJ = pIJ.add(_vRight.scale(xJ));
            if (!isZero(yI))
                pIJ = pIJ.add(_vUp.scale(yI));

        }
        return new Ray(_aperture._focalPlane, pIJ.subtract(_aperture._focalPlane));
    }

    /**
     * function that print grid of line in the scene
     * @param interval the size of the grid
     * @param color the color of the grid
     */
    public void printGrid(int interval, Color color)
    {
        _writer.printGrid(interval, color);
    }

    /**
     * Function writeToImage produces png file of the image according to
     * pixel color matrix in the directory of the project
     */
    public Camera writeToImage() {
        _writer.writeToImage();
        return this;
    }

    public Camera build() {
        return this;
    }

    private class Aperture{
        private double _distanceFromCamera=_distance/2;
        /**
         * the width of the Aperture
         */
        private double _width;
        /**
         * the height of the Aperture
         */
        private double _height;

        private Point _focalPlane;

        public Aperture(double distance,double width,double height,Point focalPlane){
            this._distanceFromCamera=distance;
            this._height=height;
            this._width=width;
            this._focalPlane =focalPlane;// ברירת מחדל לפוקל פוינט

        }
    }
}
