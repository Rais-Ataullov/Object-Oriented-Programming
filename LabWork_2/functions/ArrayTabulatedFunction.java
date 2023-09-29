package functions;

import java.util.Arrays;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction{

    protected int count;
    public double[] arrayOfX;
    public double[] arrayOfY;

    public int getCount(){
        return count;
    }

    public double getX(int index){
        return arrayOfX[index];
    }

    public double getY(int index){
        return arrayOfY[index];
    }

    public void setY(int index, double value){
        arrayOfY[index] = value;
    }

    public int indexOfX(double x){
        int index = 0;
        while(index!=count){
            if(arrayOfX[index] == x) return index;
            index++;
        }
        return -1;
    }

    public int indexOfY(double y){
        int index = 0;
        while(index!=count){
            if(arrayOfX[index] == y) return index;
            index++;
        }
        return -1;
    }

    public double leftBound(){
        return arrayOfX[0];
    }

    public double rightBound() {
        return arrayOfX[count - 1];
    }

    ArrayTabulatedFunction(double[] xValues, double[] yValues){
        count = xValues.length;
        arrayOfX = Arrays.copyOf(xValues, count);
        arrayOfY = Arrays.copyOf(yValues, count);

    }
    protected int floorIndexOfX(double x){
        int index = 0;
        while(arrayOfX[index] < x && index !=count) index++;
        return index;
    }
    protected double extrapolateLeft(double x){
        if(count == 1) return arrayOfY[0];
        double newY = arrayOfY[0] + (arrayOfY[1]-arrayOfY[0])/
                (arrayOfX[1] - arrayOfX[0]) * (x - arrayOfY[0]);
        double[] newArrayOfX = new double[count+1];
        double[] newArrayOfY = new double[count+1];
        newArrayOfX[0] = x;
        newArrayOfY[0] = newY;
        int index = 1;
        while(index!=count){
            newArrayOfX[index] = arrayOfX[index-1];
            newArrayOfY[index] = arrayOfY[index-1];
            ++index;
        }
        arrayOfX = newArrayOfX;
        arrayOfY = newArrayOfY;
        count++;
        return newY;
    }

    protected double extrapolateRight(double x){
        if(count == 1) return arrayOfY[0];
        double newY =  arrayOfY[count-2] + (arrayOfY[count-1] - arrayOfY[count-2])/
                (arrayOfX[count-1] - arrayOfX[count-2]) * (x - arrayOfX[count-2]);
        double[] newArrayOfX = new double[count+1];
        double[] newArrayOfY = new double[count+1];
        newArrayOfX[count] = x;
        newArrayOfY[count] = newY;
        int index = 0;
        while(index!=count-1){
            newArrayOfX[index] = arrayOfX[index];
            newArrayOfY[index] = arrayOfY[index];
            ++index;
        }
        arrayOfX = newArrayOfX;
        arrayOfY = newArrayOfY;
        count++;
        return newY;
    }

    protected double interpolate(double x, int floorIndex){
        if(count == 1) return arrayOfY[0];
        double newY =  arrayOfY[floorIndex-1] + (arrayOfY[floorIndex] - arrayOfY[floorIndex-1])/
                (arrayOfX[floorIndex] - arrayOfX[floorIndex-1]) * (x - arrayOfX[floorIndex-1]);
        double[] newArrayOfX = new double[count+1];
        double[] newArrayOfY = new double[count+1];
        newArrayOfX[floorIndex] = x;
        newArrayOfY[floorIndex] = newY;
        int index = 0;
        while(index!=floorIndex){
            newArrayOfX[index] = arrayOfX[index];
            newArrayOfY[index] = arrayOfY[index];
            ++index;
        }
        ++index;
        while(index!=count){
            newArrayOfX[index] = arrayOfX[index];
            newArrayOfY[index] = arrayOfY[index];
            ++index;
        }
        arrayOfX = newArrayOfX;
        arrayOfY = newArrayOfY;
        count++;
        return newY;
    }

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY){
        if (leftX>rightX){
            double temp = rightX;
            rightX = leftX;
            leftX = temp;
        }
        if(count == 1) return arrayOfY[0];
        double newY =  leftY + (rightY - leftY)/
                (rightX - leftX) * (x - leftX);
        int floorIndex = floorIndexOfX(x);
        double[] newArrayOfX = new double[count+1];
        double[] newArrayOfY = new double[count+1];
        newArrayOfX[floorIndex] = x;
        newArrayOfY[floorIndex] = newY;
        int index = 0;
        while(index!=floorIndex){
            newArrayOfX[index] = arrayOfX[index];
            newArrayOfY[index] = arrayOfY[index];
            ++index;
        }
        ++index;
        while(index!=count+1){
            newArrayOfX[index] = arrayOfX[index];
            newArrayOfY[index] = arrayOfY[index];
            ++index;
        }
        arrayOfX = newArrayOfX;
        arrayOfY = newArrayOfY;
        count++;
        return newY;
    }
    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int countOfThisValues){
        arrayOfY = new double[countOfThisValues];
        arrayOfX = new double[countOfThisValues];
        count = countOfThisValues;
        arrayOfX[0] = xFrom; arrayOfY[0] = source.apply(xFrom);
        arrayOfX[countOfThisValues-1] = xTo; arrayOfY[countOfThisValues-1] = source.apply(xTo);
        double samplingFrequency = (xTo-xFrom)/(countOfThisValues-1);
        double currentX = xFrom+samplingFrequency;
        int current_index = 1;
        while(current_index!=countOfThisValues-1){
            arrayOfX[current_index] = currentX;
            arrayOfY[current_index] = source.apply(currentX);
            ++current_index;
            currentX+=samplingFrequency;
        }
    }

    public double apply(double x){
        double result;
        if(x<arrayOfX[0]) result = this.extrapolateLeft(x);
        else if(x>arrayOfX[count-1])result = this.extrapolateRight(x);
        else{
            int indexOfX = indexOfX(x);
            if(indexOfX>0) result = this.arrayOfY[indexOfX];
            else result = this.interpolate(x, floorIndexOfX(x));
        }
        return result;
    }
}
