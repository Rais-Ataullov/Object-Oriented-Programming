package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NaturalLogarithmTest {

    @Test
    void apply() {

        MathFunction func = new NaturalLogarithm();
        double actual = func.apply(5);
        double expected = 1.60944;
        assertEquals(actual, expected, 0.00001);
    }
}