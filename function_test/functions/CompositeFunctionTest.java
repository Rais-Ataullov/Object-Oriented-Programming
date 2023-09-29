package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositeFunctionTest {

    @Test
    void apply() {

        /*  h(x) = g(f(x))
        *   g(x) - func2
        *   f(x) - func1 */

        MathFunction func_1 = new IdentityFunction();
        MathFunction func_2 = new NaturalLogarithm();
        MathFunction comp_func = new CompositeFunction(func_1, func_2);

         double actual = comp_func.apply(7.62);
        double expected = 2.03078;
        assertEquals(actual, expected, 0.00001);

        MathFunction comp_func_2 = new CompositeFunction(func_2, func_2);
        MathFunction comp_func_main = new CompositeFunction(comp_func, comp_func_2);

        actual = comp_func_main.apply(3.85);
        expected = -1.20839;
        assertEquals(actual, expected, 0.00001);

    }
}