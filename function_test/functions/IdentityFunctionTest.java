package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentityFunctionTest {

    @Test
    void apply() {

        MathFunction func = new IdentityFunction();
        double actual = func.apply(5.5);
        double expected = 5.5;
        assertEquals(actual, expected, 0.0);
    }
}