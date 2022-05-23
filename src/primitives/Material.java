package primitives;

public class Material {
    public Double3 kS = Double3.ZERO;
    public Double3 kD = Double3.ZERO;
    public Double3 kT = Double3.ZERO; // Promotes transparency
    public Double3 kR = Double3.ZERO; // Promotes reflection
    public int nShininess = 0;


    public Double3 getkT() {
        return kT;
    }

    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    public int getShininess() {
        return nShininess;
    }


    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


    public Double3 getkS() {
        return kS;
    }
}
