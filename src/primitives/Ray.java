package primitives;

public class Ray {
    Point p0;
    Vector dir;

    /**
     *constructor
     * @param p
     * @param vec
     */
    public Ray(Point p, Vector vec){
        this.dir = vec.normalize();
        this.p0=p;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }
}
