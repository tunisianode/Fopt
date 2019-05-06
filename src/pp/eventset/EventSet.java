package pp.eventset;

public class EventSet
{
    private boolean[] feld;

    public EventSet (int lang)
    {
        if (lang < 0)
        {
            throw new IllegalArgumentException("lange muss positiv sein");
        }
        feld = new boolean[lang];
    }

    public synchronized void set (int position, boolean value)
    {
        if (position < 0 || position >= feld.length)
        {
            throw new IllegalArgumentException("pruefe die start und end ");
        }

        feld[position] = value;
        notify();
    }

    public synchronized void waitAND ( )
    {
        for (int i = 0; i < feld.length - 1; i++)
        {
            while(!feld[i])
            {
                try
                {
                    wait();
                }
                catch(InterruptedException ex)
                {
                    //
                }
            }
        }
        System.out.println("alle felder sind ture");
    }

    public synchronized void waitOR ( )
    {
        boolean value = true;
        int i = 0;
        while(i < feld.length - 1)
        {
            if (feld[i])
            {
                return;
            }
            else
            {
                i++;
                if (i == feld.length - 1)
                {
                    i = 0;
                    try
                    {
                        wait();
                    }
                    catch(InterruptedException ex)
                    {
                        //
                    }

                }
            }
        }

        System.out.println("ein feld ist true");
    }
}
