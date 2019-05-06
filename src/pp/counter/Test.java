package pp.counter;

public class Test
{

    public static void main(String[] args)
    {
        BoundedCounter bc = new BoundedCounter(0,10);

        Thread up1 = new Thread(()-> bc.up());
        Thread up2 = new Thread(()-> bc.up());
        Thread up3 = new Thread(()-> bc.up());
        Thread up4 = new Thread(()-> bc.up());
        Thread up5 = new Thread(()-> bc.up());
        Thread up6 = new Thread(()-> bc.up());
        Thread up7 = new Thread(()-> bc.up());
        Thread up8 = new Thread(()-> bc.up());
        Thread up9 = new Thread(()-> bc.up());
        Thread up10 = new Thread(()-> bc.up());
        Thread up11 = new Thread(()-> bc.up());

        Thread down1 = new Thread(()-> bc.down());
        Thread down2 = new Thread(()-> bc.down());
        Thread down3 = new Thread(()-> bc.down());
        Thread down4 = new Thread(()-> bc.down());
        Thread down5 = new Thread(()-> bc.down());
        Thread down6 = new Thread(()-> bc.down());
        Thread down7 = new Thread(()-> bc.down());
        Thread down8 = new Thread(()-> bc.down());
        Thread down9 = new Thread(()-> bc.down());
        Thread down10 = new Thread(()-> bc.down());
        Thread down11 = new Thread(()-> bc.down());
        up1.start();
        up2.start();
        up3.start();
        up4.start();
        up5.start();
        up6.start();
        up7.start();
        up8.start();
        up9.start();
        up10.start();        up11.start();

        down1.start();
        down2.start();
        down3.start();
        down4.start();
        down5.start();
        down6.start();
        down7.start();
        down8.start();
        down9.start();
        down10.start();
        down11.start();




    }
}
