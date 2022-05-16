package primitives;

public class Material {
    public Double3 _kS=Double3.ZERO;
    public Double3 _kD=Double3.ZERO;

    public Double3 getkT() {
        return kT;
    }

    public Double3 kT;
    public Double3 kR;

    public int getShininess() {
        return nShininess;
    }

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
    public Material setKt(double kT) {
        this.kT =new Double3(kT) ;
        return this;
    }
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


    public Double3 getkS() {
        return _kS;
    }
}
