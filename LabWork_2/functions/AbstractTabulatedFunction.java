package functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {

    abstract protected int floorIndexOfX(double x);

    abstract protected double extrapolateLeft(double x);

    abstract protected double extrapolateRight(double x);

    abstract protected double interpolate(double x, int floorIndex);

    abstract protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY);

}
