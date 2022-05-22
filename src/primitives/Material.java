package primitives;

public class Material {
    public Double3 _kS=Double3.ZERO;
    public Double3 _kD=Double3.ZERO;
    public Double3 _kT =Double3.ZERO; // Promotes transparency
    public Double3 _kR =Double3.ZERO; // Promotes reflection
    public int nShininess=0;


    public Double3 getkT() {
        return _kT;
    }

    public Material setkR(Double3 kR) {
        this._kR = kR;
        return this;
    }

    public int getShininess() {
        return nShininess;
    }


    public Material set_kS(Double3 kS) {
        _kS = kS;
        return this;
    }

    public Material set_kD(Double3 kD) {
        _kD = kD;
        return this;
    }
    public Material set_kS(double kS) {
        _kS=new Double3(kS);
        return this;
    }

    public Material set_kD(double kD) {
        _kD =new Double3(kD);
        return this;
    }
    public Material set_kT(double kT) {
        this._kT =new Double3(kT) ;
        return this;
    }
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


    public Double3 get_kS() {
        return _kS;
    }
}
