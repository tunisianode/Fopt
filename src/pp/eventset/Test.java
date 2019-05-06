package pp.eventset;



public class Test
{

    public static void main(String[] args)
    {

        EventSet bc = new EventSet(10);
        Thread up1 = new Thread(()->
        {
            for (int i = 0; i <10 ; i++)
            {
                bc.set(i,false);
            }

        }

        );

        Thread up2 = new Thread(()-> bc.set(0,true));
        Thread up3 = new Thread(()-> bc.set(1,true));
        Thread up4 = new Thread(()-> bc.set(2,true));
        Thread up5 = new Thread(()-> bc.waitAND());
        Thread up6 = new Thread(()-> bc.waitOR());
        Thread up7 = new Thread(()-> bc.set(3,true));
        Thread up71 = new Thread(()-> bc.set(4,true));
        Thread up8 = new Thread(()-> bc.waitOR());
        Thread up9 = new Thread(()-> bc.waitAND());


        up1.start();
        up2.start();
        up3.start();
        up4.start();
        up5.start();
        up6.start();
        up7.start();up71.start();
        up8.start();
        up9.start();




    }
}
