package pada.da.servlets.basic;

class SleepingThread extends Thread {
    private int secs;
    private String message;

    public SleepingThread(int secs) {
        this.secs = secs;
    }

    public void run() {
        try {
            Thread.sleep(secs * 1000);
            message = "Es wurde " + secs + " Sekunden geschlafen.";
        } catch (InterruptedException e) {
            message = "Es gab Probleme beim Schlafen.";
        }
    }

    public String getMessage() {
        return message;
    }
}