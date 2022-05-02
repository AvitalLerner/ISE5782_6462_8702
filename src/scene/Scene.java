package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 *  Scene or holding all the objects involved
 *  using Builder Pattern
 */
public class Scene {

    private final String name;
    private final Color background;
    private final AmbientLight ambientLight;
    private final Geometries geometries;

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this ;
    }

    List<LightSource> lights=new LinkedList<>();
    private Scene(SceneBuilder builder){
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
    }

    public String getName() {
        return name;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public Geometries getGeometries() {
        return geometries;
    }

    public static class SceneBuilder {

        private final String name;
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = new AmbientLight();
        private Geometries geometries = new Geometries();

        public SceneBuilder(String name){
            this.name = name;
        }

        //chaining methods

        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        // build
        public Scene build(){
            Scene scene = new Scene(this);
            //....
            return scene;
        }
    }
}
