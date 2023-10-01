package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTabulatedFunctionTest {

    double[] arrX = {-3,0,2};
    double[] arrY = {3,5,7};

    @Test
    void apply() {
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX,arrY);
        assertEquals(func1.apply(0),5);
        assertEquals(func1.apply(1.5),6.5);
        assertEquals(func1.apply(-5), 1.666,0.001);
        assertEquals(func1.apply(10 ),15);
    }

    @Test
    void getCount() {

        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX,arrY);
        assertEquals(func1.getCount(),3);
    }

    @Test
    void getX() {
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        assertEquals(func1.getX(0), -3);
    }

    @Test
    void getY() {
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        assertEquals(func1.getY(0),3);
    }

    @Test
    void setY() {
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        func1.setY(2,5);
        assertEquals(func1.getY(2),5);
    }

    @Test
    void indexOfX() {
        MathFunction source = new SqrFunction();
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(source,0,3,4);
        assertEquals(func1.indexOfX(2),2);
        assertEquals(func1.indexOfX(-513),-1);
    }

    @Test
    void indexOfY() {
        MathFunction source = new SqrFunction();
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(source,0,3,4);
        assertEquals(func1.indexOfY(9),3);
        assertEquals(func1.indexOfY(6315),-1);
    }

    @Test
    void leftBound() {
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        assertEquals(func1.leftBound(),-3);
    }

    @Test
    void rightBound() {
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        assertEquals(func1.rightBound(),2);
    }

    @Test
    void floorIndexOfX() {
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        assertEquals(func1.floorIndexOfX(1),1);
    }

    @Test
    void extrapolateLeft() {
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        func1.extrapolateLeft(-17);
        assertEquals(func1.getY(0), -6.333,0.001);
        assertEquals(func1.getX(0),-17);
    }

    @Test
    void extrapolateRight() {
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        func1.extrapolateRight(1501);
        assertEquals(func1.getY(func1.getCount()-1), 1506 );
        assertEquals(func1.getX(func1.getCount()-1),1501);
    }

    @Test
    void interpolate() {
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        func1.interpolate(1, func1.floorIndexOfX(1));
        assertEquals(func1.getY(2),6);
    }

    @Test
    void andThen(){

        MathFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        MathFunction func2 = new SqrFunction();
        CompositeFunction func = func2.andThen(func1);
        assertEquals(func.apply(Math.sqrt(2)),7);


        double[] newArrX = {-8,1,10,16};
        double[] newArrY = {-10,8,15,41};
        MathFunction func3 = new ArrayTabulatedFunction(arrX, arrY);
        MathFunction func4 = new ArrayTabulatedFunction(newArrX, newArrY);
        CompositeFunction compFunc = func3.andThen(func4);
        assertEquals(compFunc.apply(1),11.8888,0.0001);

    }

}