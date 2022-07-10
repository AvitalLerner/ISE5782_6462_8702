package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene or holding all the objects involved
 * using Builder Pattern
 */
public class Scene {
    /**
     * name of the scene
     */
    public String name;
    /**
     * the color of the background of the scene
     */
    public Color _background;
    /**
     * the _ambientLight of the scene
     */
    public AmbientLight _ambientLight;
    /**
     * the geometries in the scene
     */
    public Geometries geometries;
    /**
     * all the light source in the scene
     */
    public List<LightSource> lights ;


    /**
     * constructor
     * @param builder an object of type SceneBuilder
     */
    public Scene(SceneBuilder builder) {
        name = builder.name;
        _background = builder.background;
        _ambientLight = builder.ambientLight;
        geometries = builder.geometries;
        lights= builder.lights;
    }

    /**
     * setter of ambientLight
     * @param ambientLight of type AmbientLight
     * @return this
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
        return this;
    }

    public static class SceneBuilder {

        private final String name;
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = new AmbientLight();
        private Geometries geometries = new Geometries();
        private List<LightSource> lights = new LinkedList<>();

        public SceneBuilder(String name) {
            this.name = name;
        }

        //chaining methods

        /**
         * sets background color
         * @param background
         * @return
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /**
         * sets ambient color
         * @param ambientLight
         * @return
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * sets all geometries in the scene
         * @param geometries
         * @return
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        /**
         * sets all lights in the scene
         * @param lights
         * @return
         */
        public SceneBuilder setLights(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        // build
        public Scene build() {
            Scene scene = new Scene(this);
            //....
            return scene;
        }
    }
}
