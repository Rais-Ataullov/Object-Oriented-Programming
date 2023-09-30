package functions;

interface MathFunction
{
    double apply(double x);

    default CompositeFunction andThen(MathFunction afterFunction)
    {
        CompositeFunction newFunction = new CompositeFunction(this, afterFunction);
        return newFunction;
    };
}