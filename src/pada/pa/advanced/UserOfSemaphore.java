package pada.pa.advanced;

class UserOfSemaphore extends Thread {
    /*
     * die beiden Semaphore werden benutzt zum gegenseitigen
     * Ausschluss f�r die Nutzung je eines Objekts
     */
    private Semaphore s1, s2;

    public UserOfSemaphore(Semaphore s1, Semaphore s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            s1.p();
            s2.p();
            // Nutzung der beiden Objekte
            s2.v();
            s1.v();
        }
    }

    public static void main(String[] args) {
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(1);
        UserOfSemaphore user1 = new UserOfSemaphore(s1, s2);
        user1.start();
        UserOfSemaphore user2 = new UserOfSemaphore(s2, s1);
        user2.start();
    }
}