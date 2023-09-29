package myfirstpackage;

public class MySecondClass
{
    private int i, j;

    public MySecondClass(int val1, int val2) { i = val1; j = val2; }

    public void setValue1(int val1) { this.i = val1; }

    public void setValue2(int val2) { this.j = val2; }

    public int getValue1() { return i; }

    public int getValue2() { return j; }

    public int Subtraction() { return this.i - this.j; }
}