package functions;

import org.junit.jupiter.api.IndicativeSentencesGeneration;
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
    @Test
    void andThenTest(){
        MathFunction func1 = new IdentityFunction();
        MathFunction func2 = new NaturalLogarithm();
        CompositeFunction func = func1.andThen(func2);
        assertEquals(func.apply(Math.E),1);
    }

    @Test
    void ToStringTest(){

        MathFunction func = new IdentityFunction();

        assertTrue(func.toString().equals("Класс является реализацией функции f(x)=x, т.е. каждое значение функции равно аргументу функции."));

    }

    @Test
    void equalsTest(){

        IdentityFunction func1 = new IdentityFunction();
        IdentityFunction func2 = new IdentityFunction();
        assertTrue(func1.equals(func2));

    }

    @Test
    void HashCodeTest(){

        IdentityFunction func1 = new IdentityFunction();
        assertEquals(func1.hashCode(), 172032696);

    }

    @Test
    void cloneTest(){

        IdentityFunction func1 = new IdentityFunction();
        IdentityFunction func2 = func1.clone();
        assertTrue(func1.equals(func2));

    }

}