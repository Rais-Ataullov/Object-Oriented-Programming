package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqrFunctionTest {

    @Test
    void apply() {
        MathFunction func = new SqrFunction();
        double actual = func.apply(2);
        double expected = 4;
        assertEquals(actual, expected);
    }

    @Test
    void andThen() {
        MathFunction func1 = new SqrFunction();
        MathFunction func2 = new SqrFunction();
        CompositeFunction func3 = func1.andThen(func2);
        assertEquals(func3.apply(5), 625);
    }
}