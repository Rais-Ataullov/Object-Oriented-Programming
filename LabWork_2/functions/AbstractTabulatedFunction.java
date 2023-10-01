package functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {

    protected int count;
    abstract protected int floorIndexOfX(double x);

    abstract protected double extrapolateLeft(double x);

    abstract protected double extrapolateRight(double x);

    abstract protected double interpolate(double x, int floorIndex);

    abstract protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY);

    public double apply(double x){
        double result;
        if(x<getX(0)) result = this.extrapolateLeft(x);
        else if(x>getX(count-1))result = this.extrapolateRight(x);
        else{
            int indexOfX = indexOfX(x);
            if(indexOfX>0) result = this.getY(indexOfX);
            else result = this.interpolate(x, floorIndexOfX(x));
        }
        return result;
    }
}
