package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient light for all objects in the scene
 */
public class AmbientLight extends Light {


    /**
     * primary constructor
     * @param Ia basic illumination
     * @param Ka attenuation factor
     */
    public AmbientLight(Color Ia , Double3 Ka){
       Light(Ka.product(Ia));
    }

    /**
     * default constructor setting ambientLight to Black
     */
    public void AmbientLight(){
        AmbientLight(Color.BLACK,);
    }

}
