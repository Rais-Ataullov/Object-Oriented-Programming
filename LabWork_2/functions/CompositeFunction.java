package functions;

class CompositeFunction implements MathFunction
{
    private MathFunction FirstFunction;
    private  MathFunction SecondFuction;

    public CompositeFunction(MathFunction func1, MathFunction func2)
    {
        FirstFunction = func1;
        SecondFuction = func2;
    }

    public double apply(double x)
    {
        return SecondFuction.apply(FirstFunction.apply(x));
    }
}