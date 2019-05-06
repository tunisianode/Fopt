package pada.pa.advanced;

class Account
{
    private float balance;

    public void debitOrCredit(float amount)
    {
        balance += amount;
    }

    public float getBalance()
    {
        return balance;
    }
}

class Bank
{
    private Account[] account;

    public Bank()
    {
        account = new Account[100];
        for(int i = 0; i < account.length; i++)
        {
            account[i] = new Account();
        }
    }

    public void transferMoney(int fromAccountNumber, int toAccountNumber,
                              float amount)
    {
        synchronized(account[fromAccountNumber])
        {
            synchronized(account[toAccountNumber])
            {
                account[fromAccountNumber].debitOrCredit(-amount);
                account[toAccountNumber].debitOrCredit(amount);
            }
        }
    }
}

class Clerk extends Thread
{
    private Bank bank;

    public Clerk(String name, Bank bank)
    {
        super(name);
        this.bank = bank;
        start();
    }

    public void run()
    {
        for(int i = 0; i < 10000; i++)
        {
            /*
             * Nummer eines Kontos einlesen, von dem abgebucht wird;
             * simuliert durch Wahl einer Zufallszahl zwischen 0 und
             * 99
             */
            int fromAccountNumber = (int) (Math.random() * 100);
            /*
             * Nummer eines Kontos einlesen, auf das abgebucht wird;
             * simuliert durch Wahl einer Zufallszahl zwischen 0 und
             * 99
             */
            int toAccountNumber = (int) (Math.random() * 100);
            /*
             * Überweisungsbetrag einlesen; simuliert durch Wahl einer
             * Zufallszahl zwischen -500 und +499
             */
            float amount = (int) (Math.random() * 1000) - 500;
            bank.transferMoney(fromAccountNumber, toAccountNumber, amount);
        }
    }
}

public class Banking
{
    public static void main(String[] args)
    {
        Bank myBank = new Bank();
        new Clerk("Andrea Müller", myBank);
        new Clerk("Petra Schmitt", myBank);
    }
}