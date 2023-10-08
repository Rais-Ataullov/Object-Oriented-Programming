package functions;

import java.util.Arrays;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable {

    protected double[] arrayOfX;
    protected double[] arrayOfY;


    public int getCount() {

        return count;

    }


    public double getX(int index) {
        return arrayOfX[index];
    }


    public double getY(int index) {
        return arrayOfY[index];
    }


    public void setY(int index, double value) {
        arrayOfY[index] = value;
    }


    public int indexOfX(double x) {

        int index = 0;

        while (index != count) {
            if (arrayOfX[index] == x) return index;
            index++;
        }

        return -1;

    }


    public int indexOfY(double y) {

        int index = 0;

        while (index != count) {
            if (arrayOfY[index] == y) return index;
            index++;
        }

        return -1;

    }

    public double leftBound() {
        return arrayOfX[0];
    }


    public double rightBound() {
        return arrayOfX[count - 1];
    }


    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        count = xValues.length;

        arrayOfX = Arrays.copyOf(xValues, count);
        arrayOfY = Arrays.copyOf(yValues, count);

    }

    protected int floorIndexOfX(double x) {
        if (x > arrayOfX[count - 1]) return count - 1;

        if (x < arrayOfX[0]) return 0;

        int index = 1;
        while (arrayOfX[index] < x) index++;
        return index - 1;

    }


    private double interpolationFormula(int leftIndex, int rightIndex, double x) {

        return arrayOfY[leftIndex] + ((arrayOfY[rightIndex] - arrayOfY[leftIndex]) /
                (arrayOfX[rightIndex] - arrayOfX[leftIndex])) * (x - arrayOfX[leftIndex]);

    }


    protected double extrapolateLeft(double x) {
        if (count == 1) return arrayOfY[0];

        double newY = interpolationFormula(0, 1, x);

        insert(x,newY);

        return newY;

    }


    protected double extrapolateRight(double x) {

        if (count == 1) return arrayOfY[0];

        double newY = interpolationFormula(count - 2, count - 1, x);

        insert(x,newY);

        return newY;

    }


    protected double interpolate(double x, int floorIndex) {

        if (count == 1) return arrayOfY[0];

        double newY = interpolationFormula(floorIndex, floorIndex + 1, x);

        insert(x,newY);

        return newY;

    }


    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int countOfThisValues) {

        arrayOfY = new double[countOfThisValues];
        arrayOfX = new double[countOfThisValues];
        count = countOfThisValues;

        arrayOfX[0] = xFrom;
        arrayOfY[0] = source.apply(xFrom);

        arrayOfX[countOfThisValues - 1] = xTo;
        arrayOfY[countOfThisValues - 1] = source.apply(xTo);

        double samplingFrequency = (xTo - xFrom) /
                (countOfThisValues - 1);
        double currentX = xFrom + samplingFrequency;

        int current_index = 1;
        while (current_index != countOfThisValues - 1) {
            arrayOfX[current_index] = currentX;
            arrayOfY[current_index] = source.apply(currentX);
            ++current_index;
            currentX += samplingFrequency;
        }

    }


    public double apply(double x) {

        double result;

        if (x < getX(0)) result = this.extrapolateLeft(x);

        else if (x > getX(count - 1)) result = this.extrapolateRight(x);

        else {

            int indexOfX = indexOfX(x);

            if (indexOfX > 0) result = this.getY(indexOfX);

            else result = this.interpolate(x, floorIndexOfX(x));

        }

        return result;

    }

    @Override
    public void insert(double x, double y) {
        int index = indexOfX(x);

        if (index > 0) {

            arrayOfY[index] = y;

        } else {

            double[] newArrayOfX = new double[count + 1];
            double[] newArrayOfY = new double[count + 1];

            if (getX(0) > x) {

                newArrayOfX[0] = x;
                newArrayOfY[0] = y;

                System.arraycopy(arrayOfX, 0, newArrayOfX, 1, count);
                System.arraycopy(arrayOfY, 0, newArrayOfY, 1, count);

            } else if (getX(count - 1) < x) {

                System.arraycopy(arrayOfX, 0, newArrayOfX, 0, count);
                System.arraycopy(arrayOfY, 0, newArrayOfY, 0, count);

                newArrayOfX[count] = x;
                newArrayOfY[count] = y;

            } else {

                int floorIndex = floorIndexOfX(x);

                System.arraycopy(arrayOfX, 0, newArrayOfX, 0, floorIndex + 1);
                System.arraycopy(arrayOfY, 0, newArrayOfY, 0, floorIndex + 1);

                newArrayOfX[floorIndex + 1] = x;
                newArrayOfY[floorIndex + 1] = y;

                System.arraycopy(arrayOfX, floorIndex + 1, newArrayOfX, floorIndex + 2, count - floorIndex - 1);
                System.arraycopy(arrayOfY, floorIndex + 1, newArrayOfY, floorIndex + 2, count - floorIndex - 1);

            }

            arrayOfX = newArrayOfX;
            arrayOfY = newArrayOfY;
            count++;

        }


    }

    @Override
    public void remove(int index) {

        double[] newArrayOfX = new double[count - 1];
        double[] newArrayOfY = new double[count - 1];

        if (index == 0) {

            System.arraycopy(arrayOfX, 1, newArrayOfX, 0, count - 1);
            System.arraycopy(arrayOfY, 1, newArrayOfY, 0, count - 1);

        }

        if (index == count - 1) {

            System.arraycopy(arrayOfX, 0, newArrayOfX, 0, count - 1);
            System.arraycopy(arrayOfY, 0, newArrayOfY, 0, count - 1);

        } else {

            System.arraycopy(arrayOfX, 0, newArrayOfX, 0, index);
            System.arraycopy(arrayOfY, 0, newArrayOfY, 0, index);

            System.arraycopy(arrayOfX, index + 1, newArrayOfX, index, count - index - 1);
            System.arraycopy(arrayOfY, index + 1, newArrayOfY, index, count - index - 1);

        }

        arrayOfX = newArrayOfX;
        arrayOfY = newArrayOfY;
        count--;


    }

    @Override
    public String toString(){

        return Arrays.toString(arrayOfX) + '\n' + Arrays.toString(arrayOfY);

    }

    @Override
    public boolean equals(Object o){

        return this.getClass() == o.getClass() &&
                Arrays.equals(((ArrayTabulatedFunction) o).arrayOfX, arrayOfX) &&
                Arrays.equals(((ArrayTabulatedFunction) o).arrayOfY, arrayOfY);

    }

    @Override
    public int hashCode(){

        return 31 * count + 37 * Arrays.hashCode(arrayOfX) + 131 * Arrays.hashCode(arrayOfY);

    }

    @Override
    public Object clone()  {

        return new ArrayTabulatedFunction(arrayOfX.clone(), arrayOfY.clone());

    }
}