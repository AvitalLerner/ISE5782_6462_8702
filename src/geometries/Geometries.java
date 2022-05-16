package geometries;

import primitives.Ray;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Composite class to gather other {@link Geometry} based objects
 */
public class Geometries extends Intersectable {
   private List<Intersectable> _intersectables=null;

    /**
     * constructor of Geometries
     * @param intersectables array of{@link Intersectable} objects
     */
   public Geometries(Intersectable...intersectables)
   {
       _intersectables=new LinkedList<>();
       Collections.addAll(_intersectables,intersectables);
   }

    /**
     * constructor
     */
    public Geometries()
    {
        _intersectables=new LinkedList<>();
    }

//    /**
//     *
//     * @param ray Ray pointing towards the graphic object
//     * @return intersection of the items
//     */
//    @Override
//    public List<Point> findIntersections(Ray ray) {
//       List<Point> result=null;
//       for(Intersectable item:_intersectables) {
//           List<Point> itemPointsList=item.findIntersections(ray);
//           if(itemPointsList!=null){
//               if(result==null){
//                   result=new LinkedList<>();
//               }
//               result.addAll(itemPointsList);
//           }
//       }
//        return result;
//    }

    /**
     *
     * find the intersection of GeoPoint with ray "r"
     * @param ray
     * @param distance
     * @return list of intersection groPoint
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double distance) {
       List<GeoPoint> result=null;
       for(Intersectable item:_intersectables) {
           List<GeoPoint> itemPointsList=item.findGeoIntersections(ray,distance);
           if(itemPointsList!=null){
               if(result==null){
                   result=new LinkedList<>();
               }
               result.addAll(itemPointsList);
           }
       }
        return result;
    }


    /**
     * add shape to collection
     * @param _intersectable Intersectable to add to the collection
     */
    public void add(Intersectable..._intersectable)
    {
        Collections.addAll(_intersectables,_intersectable);
    }
}
