package pada.pa.advanced;

class X {
    private X otherX;

    public void setPartner(X otherX) {
        this.otherX = otherX;
    }

    public synchronized void m1() {
        otherX.m2();
    }

    public synchronized void m2() {
        // nicht weiter wichtig
    }
}

public class UserOfX extends Thread {
    private X myX;

    public UserOfX(X x) {
        myX = x;
    }

    public void run() {
        for (int i = 0; i < 10000; i++)
            myX.m1();
    }

    public static void main(String[] args) {
        X x1 = new X();
        X x2 = new X();
        x1.setPartner(x2);
        x2.setPartner(x1);
        UserOfX user1 = new UserOfX(x1);
        user1.start();
        UserOfX user2 = new UserOfX(x2);
        user2.start();
    }
}