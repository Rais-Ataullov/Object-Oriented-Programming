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

    @Test
    void compositeFunctionTestOfAndThen(){

        MathFunction func_1 = new NaturalLogarithm();
        MathFunction func_2 = new SqrFunction();
        MathFunction func_3 = new AdditionalFunction();
        CompositeFunction func = (func_3.andThen(func_2)).andThen(func_1);
        assertEquals(func.apply(0),0.90316,0.00001);

        MathFunction func_5 = new NaturalLogarithm();
        MathFunction func_6 = new ConstantFunction(1);
        CompositeFunction newFunc = func_6.andThen(func_5);
        assertEquals(newFunc.apply(513), 0);

    }



}