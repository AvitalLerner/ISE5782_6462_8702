package primitives;

public class Material {
    public Double3 _kS=new Double3(0);
    public Double3 _kD=new Double3(0);
    public int nShininess=0;

    public Material setkS(Double3 kS) {
        _kS = kS;
        return this;
    }

    public Material setkD(Double3 kD) {
        _kD = kD;
        return this;
    }
    public Material setkS(double kS) {
        _kS=new Double3(kS);
        return this;
    }

    public Material setkD(double kD) {
        _kD =new Double3(kD);
        return this;
    }
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
