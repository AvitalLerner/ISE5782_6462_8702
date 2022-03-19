package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Test class for{@link Vector}
 */
class VectorTest {
    Vector v1=new Vector(1,2,3);
    Vector v2=new Vector(-2,-4,-6);
    Vector v3=new Vector(0,3,-2);

    /**
     * Test method for{@link Vector#Vector(Double3)}
     */
    @Test
    public void testConstructorZero(){
        assertThrows(
                IllegalArgumentException.class,
                () -> {new Vector(0,0,0);},
                "ERROR: zero vector should have thrown an exception"  );
        }

    /**
     * Test method for{@link Vector#lengthSquared()}
     */
    @Test
    public void testLengthSquared() {
        assertEquals(v1.lengthSquared(),14,"Doesn't calculates the length squared correctly ");
    }

    /**
     * Test method for{@link Vector#length()}
     */
    @Test
    public void testLength() {
        assertEquals(new Vector(0,3,4).length(),5,"Doesn't calculates the length squared correctly ");
    }

    /**
     * Test method for{@link Vector#dotProduct(Vector)}
     */
    @Test
    public void testDotProduct() {
        assertEquals(v1.dotProduct(v3),0,"ERROR: dotProduct() for orthogonal vectors is not zero");
        assertEquals(0,v1.dotProduct(v2) + 28,"ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for{@link Vector#crossProduct(Vector)}
     */
    @Test
    public void testCrossProduct() {
        assertThrows(
        IllegalArgumentException.class,
                () -> {v1.crossProduct(v2);},
                "ERROR: crossProduct() for parallel vectors does not throw an exception");
        Vector vr = v1.crossProduct(v3);
        assertEquals(v1.length() * v3.length() ,vr.length() ,0.00001,"ERROR: crossProduct() wrong result length");
        assertEquals(0,vr.dotProduct(v1),"ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(0,vr.dotProduct(v3),"ERROR: crossProduct() result is not orthogonal to its operands");

    }

    /**
     * Test method for{@link Vector#scale(double)}
     */
    @Test
    public void testScale() {
        assertThrows(
                IllegalArgumentException.class,
                () -> {v1.scale(0);},
                "ERROR: scale(0) not valid and does not throw an exception ");

        assertEquals(new Point(2,0,0),(new Vector(1,0,0)).scale(2),"ERROR: scale() result is incorrect");
    }

    /**
     * Test method for{@link Vector#add(Vector)}
     */
    @Test
    public void testAdd() {
        assertThrows(
                IllegalArgumentException.class,
                () -> { v1.add(new Vector(-1,-2,-3));},
                "ERROR: add() resulting by 0 not valid and does not throw an exception");
    }

    /**
     * Test method for{@link Vector#normalize()}
     */
    @Test
    public void testNormalize() {
        Vector vector=v1.normalize();
        assertEquals(1,vector.length(),"ERROR: the normalized vector is not a unit vector");
        assertThrows(
                IllegalArgumentException.class,
                () -> { v1.crossProduct(vector);},
                "ERROR: the normalized vector is not parallel to the original one");
        assertTrue(v1.dotProduct(vector)>=0,"ERROR: the normalized vector is opposite to the original one");
    }
}