package pada.pa.advanced;

class State
{
    public static final int THINKING = 0;
    public static final int HUNGRY = 1;
    public static final int EATING = 2;
    private int[] state;

    public State(int number)
    {
        state = new int[number];
        for(int i = 0; i < number; i++)
            state[i] = THINKING;
    }

    public int leftNeighbor(int i)
    {
        if(i - 1 >= 0)
            return i - 1;
        else
            return state.length - 1;
    }

    public int rightNeighbor(int i)
    {
        if(i + 1 < state.length)
            return i + 1;
        else
            return 0;
    }

    public void set(int index, int value)
    {
        state[index] = value;
    }

    public boolean isEatingPossible(int i)
    {
        if(state[i] == HUNGRY && state[leftNeighbor(i)] != EATING
           && state[rightNeighbor(i)] != EATING)
            return true;
        return false;
    }
}

public class PhilosopherWithSemaphoresGood implements Runnable
{
    private Semaphore mutex;
    private Semaphore[] eatingAllowed;
    private State state;
    private int number, leftNeighbor, rightNeighbor;

    public PhilosopherWithSemaphoresGood(Semaphore mutex,
                                         Semaphore[] eatingAllowed,
                                         State state, int number)
    {
        this.mutex = mutex;
        this.eatingAllowed = eatingAllowed;
        this.state = state;
        this.number = number;
        this.leftNeighbor = state.leftNeighbor(number);
        this.rightNeighbor = state.rightNeighbor(number);
    }

    public void run()
    {
        while(true)
        {
            think(number);
            /* zum Essen anmelden; prüfen, ob E. möglich */
            mutex.p();
            state.set(number, State.HUNGRY);
            if(state.isEatingPossible(number))
            {
                state.set(number, State.EATING);
                eatingAllowed[number].v();
            }
            mutex.v();
            /* auf Essenserlaubnis warten und essen */
            eatingAllowed[number].p();
            eat(number);
            /* vom Essen abmelden; für Nachbarn prüfen */
            mutex.p();
            state.set(number, State.THINKING);
            if(state.isEatingPossible(leftNeighbor))
            {
                state.set(leftNeighbor, State.EATING);
                eatingAllowed[leftNeighbor].v();
            }
            if(state.isEatingPossible(rightNeighbor))
            {
                state.set(rightNeighbor, State.EATING);
                eatingAllowed[rightNeighbor].v();
            }
            mutex.v();
        }
    }

    private void think(int number)
    {
        System.out.println("Philosoph " + number + " denkt.");
        try
        {
            Thread.sleep((int) (Math.random() * 20000));
        }
        catch(InterruptedException e)
        {
        }
    }

    private void eat(int number)
    {
        System.out.println("Philosoph " + number + " fängt zu essen an.");
        try
        {
            Thread.sleep((int) (Math.random() * 20000));
        }
        catch(InterruptedException e)
        {
        }
        System.out.println("Philosoph " + number + " hört auf zu essen.");
    }
    private static final int NUMBER = 5;

    public static void main(String[] args)
    {
        Semaphore mutex = new Semaphore(1);
        Semaphore[] eatingAllowed;
        eatingAllowed = new Semaphore[NUMBER];
        for(int i = 0; i < NUMBER; i++)
            eatingAllowed[i] = new Semaphore(0);
        State s = new State(NUMBER);
        for(int i = 0; i < NUMBER; i++)
            new Thread(new PhilosopherWithSemaphoresGood(mutex, eatingAllowed,
                                                         s, i)).start();
    }
}