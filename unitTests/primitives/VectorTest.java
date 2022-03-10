package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    public void testConstructorZero(){
        assertThrows(
                IllegalArgumentException.class,
                () -> {new Vector(0,0,0);},
                "ERROR: zero vector should have thrown an exception"  );
        }

    @Test
    public void testLengthSquared() {
    }

    @Test
    public void testLength() {
    }

    @Test
    public void testDotProduct() {
    }

    @Test
    public void testCrossProduct() {
    }

    @Test
    public void testScale() {
    }

    @Test
    public void testAdd() {
    }

    @Test
    public void testNormalize() {
    }
}