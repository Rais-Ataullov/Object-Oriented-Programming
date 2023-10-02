package functions;

class CompositeFunction implements MathFunction
{
    private MathFunction FirstFunction;
    private  MathFunction SecondFunction;

    public CompositeFunction(MathFunction func1, MathFunction func2)
    {
        FirstFunction = func1;
        SecondFunction = func2;
    }

    public double apply(double x)
    {
        return SecondFunction.apply(FirstFunction.apply(x));
    }
}