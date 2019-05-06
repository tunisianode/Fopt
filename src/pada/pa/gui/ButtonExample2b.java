package pada.pa.gui;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ButtonExample2b extends ButtonExample2
{
    public void actionPerformed(ActionEvent evt)
    {
        super.actionPerformed(evt);
        try
        {
            Thread.sleep(10000);
        }
        catch(InterruptedException e)
        {
        }
    }

    public static void main(String[] args)
    {
        new ButtonExample2b();
    }
}