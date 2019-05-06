package pada.pa.advanced;

public class Pipe
{
    private byte[] buffer = null;
    private int bsize = 0;
    private int head = 0;
    private int tail = 0;

    public Pipe(int capacity)
    {
        if(capacity <= 0)
        {
            throw new IllegalArgumentException("Parameter <= 0");
        }
        buffer = new byte[capacity];
    }

    public int getCapacity()
    {
        return buffer.length;
    }

    public synchronized void send(byte[] msg)
    {
        if(msg.length <= buffer.length)
        {
            /* Senden "auf einen Schlag" */
            while(msg.length > buffer.length - bsize)
            {
                try
                {
                    wait();
                }
                catch(InterruptedException e)
                {
                }
            }
            /* Kopieren der Nachricht */
            for(int i = 0; i < msg.length; i++)
            {
                buffer[tail] = msg[i];
                tail++;
                if(tail == buffer.length)
                    tail = 0;
            }
            bsize += msg.length;
            notifyAll();
        }
        else
        {
            /* Senden in Portionen */
            int offset = 0;
            int stillToSend = msg.length;
            while(stillToSend > 0)
            {
                while(bsize == buffer.length)
                {
                    try
                    {
                        wait();
                    }
                    catch(InterruptedException e)
                    {
                    }
                }
                int sendNow = buffer.length - bsize;
                if(stillToSend < sendNow)
                {
                    sendNow = stillToSend;
                }
                for(int i = 0; i < sendNow; i++)
                {
                    buffer[tail] = msg[offset];
                    tail++;
                    if(tail == buffer.length)
                        tail = 0;
                    offset++;
                }
                bsize += sendNow;
                stillToSend -= sendNow;
                notifyAll();
            }
        }
    }

    public synchronized byte[] receive(int noBytes)
    // noBytes: number of bytes
    {
        while(bsize == 0)
        {
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
            }
        }
        if(noBytes > bsize)
        {
            noBytes = bsize;
        }
        byte[] result = new byte[noBytes];
        for(int i = 0; i < noBytes; i++)
        {
            result[i] = buffer[head];
            head++;
            if(head == buffer.length)
            {
                head = 0;
            }
        }
        bsize -= noBytes;
        notifyAll();
        return result;
    }
}