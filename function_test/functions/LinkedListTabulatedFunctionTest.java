package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTabulatedFunctionTest {

    double[] arrayX = {-152, -2.35, 0.66, 4, 15, 76.89};
    double[] arrayY = {90.7, 29.65, 20, 31.76, -67, -170};
    LinkedListTabulatedFunction TabFunc1 = new LinkedListTabulatedFunction(arrayX, arrayY);

    MathFunction source = new NaturalLogarithm();
    double xFrom = 1; double xTo = 15; int count = 8;
    LinkedListTabulatedFunction TabFunc2 =
            new LinkedListTabulatedFunction(source, xFrom, xTo, count);

    @Test
    void getCount() {
        assertEquals(TabFunc1.getCount(), 6);
        assertEquals(TabFunc2.getCount(), 8);
    }

    @Test
    void leftBound(){
        assertEquals(TabFunc1.leftBound(), -152);
        assertEquals(TabFunc2.leftBound(), 1);
    }

    @Test
    void rightBound(){
        assertEquals(TabFunc1.rightBound(), 76.89);
        assertEquals(TabFunc2.rightBound(), 15);
    }

    @Test void getX(){
        for (int i = 0; i < arrayX.length; i++){
            assertEquals(TabFunc1.getX(i), arrayX[i]); }

        double temp = (xTo - xFrom)/(count-1);
        for (int i = 0; i < count; i++){
            assertEquals(TabFunc2.getX(i), 1 + temp * i);
        }
    }

    @Test
    void getY(){
        for (int i = 0; i < arrayY.length; i++){
            assertEquals(TabFunc1.getY(i), arrayY[i]); }

        double temp = (xTo - xFrom)/(count-1);
        for (int i = 0; i < count; i++){
            assertEquals(TabFunc2.getY(i), Math.round(Math.log(1 + temp * i) * 1000.0) / 1000.0);
        }
    }

    @Test
    void setY(){
       TabFunc1.setY(3, 20.56);
       assertEquals(TabFunc1.getY(3), 20.56);
    }
    // double[] arrayY = {90.7, 29.65, 20, 20.56, -67, -170};
    @Test
    void indexOfX(){
        assertEquals(TabFunc1.indexOfX(15),4);
        assertEquals(TabFunc1.indexOfX(76.90),-1);

        assertEquals(TabFunc2.indexOfX(15),7);
        assertEquals(TabFunc2.indexOfX(8),-1);
    }

    @Test
    void indexOfY(){
        assertEquals(TabFunc1.indexOfY(-67),4);
        assertEquals(TabFunc1.indexOfY(1010),-1);

        double true_y = Math.round(Math.log(5) * 1000.0) / 1000.0;
        double some_y = Math.round(Math.log(10) * 1000.0) / 1000.0;
        assertEquals(TabFunc2.indexOfY(true_y),2);
        assertEquals(TabFunc2.indexOfY(some_y),-1);
    }

    @Test
    void floorIndexOfX(){
        assertEquals(TabFunc1.floorIndexOfX(35.76),4);
        assertEquals(TabFunc1.floorIndexOfX(-100),0);

        assertEquals(TabFunc2.floorIndexOfX(20),7);
        assertEquals(TabFunc2.floorIndexOfX(11),5);
        assertEquals(TabFunc2.floorIndexOfX(6),2);
    }

    @Test
    void extrapolateLeft(){
        double new_y = TabFunc1.extrapolateLeft(-200);
        assertEquals(new_y, 110.282,0.002);
        assertEquals(TabFunc1.getX(0),-200);
        assertEquals(TabFunc1.getY(0), new_y);

        new_y = TabFunc2.extrapolateLeft(0.5);
        assertEquals(new_y, -0.275,0.002);
        assertEquals(TabFunc2.getX(0),0.5);
        assertEquals(TabFunc2.getY(0), new_y);

    }
    /*
    * Tab_Func1:   x {-200,     -152,   -2.35,  0.66,   4,      15,     76.89}
    *              y {110.282,  90.7,   29.65,  20,     20.56,  -67,    -170}
    *
    * Tab_Func2:   x {  1,  3,      5,      7,      9,      11,     13,     15}
    *              y {  0,  1.099,  1.609,  1.950,  2.197,  2.398,  2.565,  2,708}
    * */

    @Test
    void extrapolateRight(){
        double new_y = TabFunc1.extrapolateRight(100);
        assertEquals(new_y, -208.461,0.002);
        assertEquals(TabFunc1.getX(TabFunc1.getCount() - 1),100);
        assertEquals(TabFunc1.getY(TabFunc1.getCount() - 1), new_y);

        new_y = TabFunc2.extrapolateRight(20.33);
        assertEquals(new_y, 3.089,0.002);
        assertEquals(TabFunc2.getX(TabFunc2.getCount() - 1),20.33);
        assertEquals(TabFunc2.getY(TabFunc2.getCount() - 1), new_y);

    }

    /*
     * Tab_Func1:   x {-200,     -152,   -2.35,  0.66,   4,      15,     76.89,     100}
     *              y {110.282,  90.7,   29.65,  20,     20.56,  -67,    -170,      -208.461}
     *
     * Tab_Func2:   x {  1,  3,      5,      7,      9,      11,     13,     15,    20.33}
     *              y {  0,  1.099,  1.609,  1.950,  2.197,  2.398,  2.565,  2,708  3.089}
     * */

    @Test
    void interpolate(){
        TabFunc1.setY(3, 20.56);
        TabFunc1.extrapolateLeft(-200);
        TabFunc1.extrapolateRight(100);
        int a = TabFunc1.floorIndexOfX(2);

        double new_y = TabFunc1.interpolate(2, TabFunc1.floorIndexOfX(2));
        assertEquals(new_y, 20.225,0.002);
        assertEquals(TabFunc1.indexOfX(2),4);
        assertEquals(TabFunc1.indexOfY(new_y), 4);

        TabFunc2.extrapolateLeft(0.5);
        TabFunc2.extrapolateRight(20.33);

        new_y = TabFunc2.interpolate(8, 7,9,1.950,2.197);
        assertEquals(new_y, 2.074,0.002);
        assertEquals(TabFunc2.indexOfX(8),5);
        assertEquals(TabFunc2.getY(5), new_y);

    }

    /*
     * Tab_Func1:   x {-200,     -152,   -2.35,  0.66,  2        4,      15,     76.89,     100}
     *              y {110.282,  90.7,   29.65,  20,    24.718   20.56,  -67,    -170,      -208.461}
     *
     * Tab_Func2:   x { 0.5     1,  3,      5,      7,      8       9,      11,     13,     15,    20.33}
     *              y { 0.275   0,  1.099,  1.609,  1.950,  2.076   2.197,  2.398,  2.565,  2,708  3.089}
     * */


    /*
     * Tab_Func1:   x { -152,   -2.35,  0.66,   4,      15,     76.89 }
     *              y { 90.7,   29.65,  20,     31.76,  -67,    -170 }
     *
     * Tab_Func2:   x { 1,  3,      5,      7,      9,      11,     13,     15 }
     *              y { 0,  1.099,  1.609,  1.950,  2.197,  2.398,  2.565,  2,708 }
     * */
    @Test
    void apply(){
        double res = TabFunc1.apply(0.66);
        assertEquals(res, 20);
        res = TabFunc1.apply(10);
        assertEquals(res, -22.109, 0.002);

        res = TabFunc2.apply(0);
        assertEquals(res, -0.549, 0.002);
        res = TabFunc2.apply(30);
        assertEquals(res, 3.781, 0.002);
    }

    @Test
    void andThen(){
        MathFunction func_1 = new NaturalLogarithm();
        CompositeFunction TabComp_func_1 = TabFunc1.andThen(func_1);
        assertEquals(TabComp_func_1.apply(5), 23.342, 0.002);

        MathFunction func_2 = new SqrFunction();
        CompositeFunction TabComp_func_2 = func_2.andThen(TabFunc2);
        assertEquals(TabComp_func_2.apply(10), 5.281, 0.002);

        MathFunction super_TabComp_func = TabComp_func_2.andThen(TabComp_func_1);
        assertEquals(super_TabComp_func.apply(8.5), 11.820, 0.002);
    }
}