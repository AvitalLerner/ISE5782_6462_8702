package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void testConstructorZero(){
        assertThrows(
                IllegalArgumentException.class,
                () -> {new Vector(0,0,0);},
                "ERROR: zero vector should have thrown an exception"  );
        }

    @Test
    void testLengthSquared() {
    }

    @Test
    void testLength() {
    }

    @Test
    void testDotProduct() {
    }

    @Test
    void testCrossProduct() {
    }

    @Test
    void testScale() {
    }

    @Test
    void testAdd() {
    }

    @Test
    void testNormalize() {
    }
}