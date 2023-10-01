package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantFunctionTest {

    @Test
    void apply() {
        ConstantFunction func = new ConstantFunction(2);
        assertEquals(func.apply(553.31531),2);
    }

    @Test
    void andThen() {
        ConstantFunction func1 = new ConstantFunction(2);
        ConstantFunction func2 = new ConstantFunction(4);
        CompositeFunction func3 = func2.andThen(func1);
        assertEquals(func3.apply(531), 2);
    }
}