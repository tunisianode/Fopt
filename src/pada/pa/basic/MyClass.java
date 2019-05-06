package pada.pa.basic;

class MyClass
{
    public synchronized void m1()
    {
        m2();
    }

    public synchronized void m2()
    {
    }

    public static void main(String[] args)
    {
        MyClass obj = new MyClass();
        obj.m1();
        // diese Stelle wird erreicht!!!
    }
}