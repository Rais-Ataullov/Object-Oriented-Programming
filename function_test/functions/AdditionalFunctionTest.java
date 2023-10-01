package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdditionalFunctionTest {

    @Test
    void apply() {
        AdditionalFunction func = new AdditionalFunction();
        assertEquals(func.apply(2), -3.57079, 0.00001);
    }

    @Test
    void andThen() {
        AdditionalFunction func1 = new AdditionalFunction();
        SqrFunction func2 = new SqrFunction();
        CompositeFunction func = func2.andThen(func1);
        assertEquals(func.apply(5),-26.5707,0.0001);

    }
}