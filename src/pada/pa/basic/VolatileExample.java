package pada.pa.basic;

class VolatileExample
{
    private volatile int value;

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}