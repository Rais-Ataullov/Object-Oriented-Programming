class MyFirstClass
{
    public static void main(String[] s)
    {
        MySecondClass o = new MySecondClass(16,7);

        System.out.println(o.Subtraction());

        for (int i = 1; i <= 8; i++)
        {
            for (int j = 1; j <= 8; j++)
            {
                o.setValue1(i);
                o.setValue2(j);
                System.out.print(o.Subtraction());
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}



class MySecondClass
{
    private int i, j;

    public MySecondClass(int val1, int val2) { i = val1; j = val2; }

    public void setValue1(int val1) { this.i = val1; }

    public void setValue2(int val2) { this.j = val2; }

    public int getValue1() { return i; }

    public int getValue2() { return j; }

    public int Subtraction() { return this.i - this.j; }
}

