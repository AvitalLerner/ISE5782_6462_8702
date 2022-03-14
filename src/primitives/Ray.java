package primitives;

public class Ray {
    Point _p0;
    Vector _dir;

    /**
     *constructor
     * @param p
     * @param vec
     */
    public Ray(Point p, Vector vec){
        this._dir = vec.normalize();
        this._p0 =p;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + _p0 +
                ", dir=" + _dir +
                '}';
    }

    public Point getP0() {
        return _p0;
    }

    public Vector getDir() {
        return _dir;
    }
}
