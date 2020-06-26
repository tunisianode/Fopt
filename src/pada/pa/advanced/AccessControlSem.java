package pada.pa.advanced;

abstract class AccessControlSem {
    private static final int MAX = 1000;
    private AdditiveSemaphore sem = new AdditiveSemaphore(MAX);

    protected abstract Object reallyRead();

    protected abstract void reallyWrite(Object obj);

    public Object read() {
        beforeRead();
        Object obj = reallyRead();
        afterRead();
        return obj;
    }

    public void write(Object obj) {
        beforeWrite();
        reallyWrite(obj);
        afterWrite();
    }

    private void beforeRead() {
        sem.p(1);
    }

    private void afterRead() {
        sem.v(1);
    }

    private void beforeWrite() {
        sem.p(MAX);
    }

    private void afterWrite() {
        sem.v(MAX);
    }
}