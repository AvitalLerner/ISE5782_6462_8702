package primitives;

public class Material {
    public Double3 _kS=Double3.ZERO;
    public Double3 _kD=Double3.ZERO;
    public Double3 kR=Double3.ZERO;
    public Double3 kT=Double3.ZERO;
    public int nShininess=0;


    /**
     * @return kT
     */
    public Double3 getkT() {return kT;}

    /**
     * @return nShininess
     */
    public int getShininess() {return nShininess;}

    /**
     * @return kS
     */
    public Double3 getkS() {return _kS;}

    /**
     * setter of kS
     * @param kS
     * @return this
     */
    public Material setkS(Double3 kS) {
        _kS = kS;
        return this;
    }

    /**
     * setter of kS
     * @param kS
     * @return this
     */
    public Material setkS(double kS) {
        _kS=new Double3(kS);
        return this;
    }

    /**
     * setter of kR
     * @param kR
     * @return this
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setter of kD
     * @param kD
     * @return this
     */
    public Material setkD(double kD) {
        _kD =new Double3(kD);
        return this;
    }

    /**
     * setter of kD
     * @param kD
     * @return this
     */
    public Material setkD(Double3 kD) {
        _kD = kD;
        return this;
    }

    /**
     * setter of kT
     * @param kT
     * @return this
     */
    public Material setkT(double kT) {
        this.kT =new Double3(kT) ;
        return this;
    }

    /**
     * setter of nShininess
     * @param nShininess
     * @return this
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}