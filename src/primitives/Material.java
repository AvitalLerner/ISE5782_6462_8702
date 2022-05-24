package primitives;

/**
 * class Material to describe the material of geometries
 * the class describe Promotes transparency, Promotes reflection,
 */
public class Material {
    public Double3 kS = Double3.ZERO;
    public Double3 kD = Double3.ZERO;
    /**
     * Promotes transparency
     */
    public Double3 kT = Double3.ZERO;
    /**
     * Promotes reflection
     */
    public Double3 kR = Double3.ZERO;
    public int nShininess = 0;


    /**
     * setter of KT
     * @param kT of type double
     * @return this
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * setter of KT
     * @param kT of type Double3
     * @return this
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setter of KR
     * @param kR of type Double3
     * @return this
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setter of KR
     * @param kR of type double
     * @return this
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * setter of KS
     * @param kS of type double
     * @return this
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * setter of KS
     * @param kS of type Double3
     * @return this
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setter of KD
     * @param kD of type Double3
     * @return this
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setter of KD
     * @param kD of type double
     * @return this
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * getter of nShininess
     * @return nShininess
     */
    public int getShininess() {
        return nShininess;
    }

    /**
     * setter of nShininess
     * @param nShininess of type int
     * @return this
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

}
