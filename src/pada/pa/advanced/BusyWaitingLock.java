package pada.pa.advanced;

import java.util.concurrent.atomic.AtomicInteger;

public class BusyWaitingLock
{
    private AtomicInteger numberOfLocks;
    
    public BusyWaitingLock()
    {
        numberOfLocks = new AtomicInteger(0);
    }
    
    public void lock()
    {
        while(!numberOfLocks.compareAndSet(0, 1));
    }
    
    public void unlock()
    {
        numberOfLocks.set(0);
    }

    public static void main(String[] args)
    {
        BusyWaitingLock lock = new BusyWaitingLock();
        new LockThread("Thread 1", lock).start();
        new LockThread("Thread 2", lock).start();
        new LockThread("Thread 3", lock).start();
        new LockThread("Thread 4", lock).start();
        new LockThread("Thread 5", lock).start();
    }
}

class LockThread extends Thread
{
    private BusyWaitingLock lock;

    public LockThread(String name, BusyWaitingLock lock)
    {
        super(name);
        this.lock = lock;
    }
    
    public void run()
    {
        for(int i = 1; i <= 5; i++)
        {
            lock.lock();
            System.out.println(getName() + ": locked");
            try
            {
                sleep((long)(Math.random() * 10 * 1000));
            }
            catch(InterruptedException e)
            {
            }
            System.out.println(getName() + ": unlocking");
            lock.unlock();
        }
    }
}