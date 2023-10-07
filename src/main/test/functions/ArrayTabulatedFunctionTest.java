package functions;

import com.sun.source.tree.ArrayAccessTree;
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
        assertEquals(func1.extrapolateLeft(-17), -6.333,0.001);
    }

    @Test
    void extrapolateRight() {
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        assertEquals(func1.extrapolateRight(1501),1506);
    }

    @Test
    void interpolate() {
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        assertEquals(func1.interpolate(1,func1.floorIndexOfX(1)),6);
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


        MathFunction func5 = new ArrayTabulatedFunction(arrX, arrY);
        MathFunction func6 = new LinkedListTabulatedFunction(newArrX, newArrY);
        CompositeFunction compOfTabulatedFunctions = func5.andThen(func6);
        assertEquals(compFunc.apply(1),11.8888,0.0001);

    }
    @Test
    void insertTest(){
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);

        func1.insert(-10,10);
        assertEquals(func1.getX(0),-10);
        assertEquals(func1.getY(0),10);

        func1.insert(10,53);
        assertEquals(func1.getX(4),10);
        assertEquals(func1.getY(4),53);

        func1.insert(1,-2);
        assertEquals(func1.getX(3),1);
        assertEquals(func1.getY(3),-2);

        func1.insert(0,5.66);
        assertEquals(func1.getX(2),0);
        assertEquals(func1.getY(2),5.66);


    }

    @Test
    void removeTest(){

        double[] newArrX = {-5,1,10,16,43};
        double[] newArrY = {1,8,122,41,85};

        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(newArrX, newArrY);

        func1.remove(2);
        assertEquals(func1.getX(2),16);
        assertEquals(func1.getY(2),41);

        func1.remove(0);
        assertEquals(func1.getX(0),1);
        assertEquals(func1.getY(0),8);

        func1.remove(2);
        assertEquals(func1.getX(1),16);
        assertEquals(func1.getY(1),41);

    }

    @Test
    void toStringTest(){

        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        assertEquals(func1.toString(),"[-3.0, 0.0, 2.0]" + "\n" + "[3.0, 5.0, 7.0]");

    }

    @Test
    void equalsTest(){

        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        ArrayTabulatedFunction func2 = new ArrayTabulatedFunction(arrX, arrY);
        assertTrue(func1.equals(func2));

    }

    @Test
    void hashCodeTest(){

        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        ArrayTabulatedFunction func2 = new ArrayTabulatedFunction(arrX, arrY);
        assertEquals(func1.hashCode(), -1054581067);
        assertEquals(func1.hashCode(), func2.hashCode());

    }
    
    @Test
    void cloneTest(){

        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(arrX, arrY);
        Object func2 = func1.clone();
        assertTrue(func1.equals(func2));

    }
}