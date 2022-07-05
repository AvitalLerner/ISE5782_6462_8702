package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * class AmbientLight extends of light to show the ambient light of the scene
 * the light have color
 */
public class AmbientLight extends Light {

    /**
     * primary constructor
     * @param Ia basic illumination
     * @param Ka attenuation factor
     */
    public AmbientLight(Color Ia , Double3 Ka){
       super(Ia.scale(Ka));
    }

    /**
     * default constructor setting ambientLight to Black
     */
    public AmbientLight(){
        super(Color.BLACK);
    }
}
