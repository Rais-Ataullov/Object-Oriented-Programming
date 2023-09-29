import myfirstpackage.*;

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