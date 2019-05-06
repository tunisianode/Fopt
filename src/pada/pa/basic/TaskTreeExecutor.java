package pada.pa.basic;

import java.util.ArrayList;
import java.util.List;

interface Task<T>
{
    public boolean isDivisible();
    public List<Task<T>> split();
    public T execute();
    public T combine(List<T> results);
}

class TaskNodeExecutor<T> extends Thread
{
    private Task<T> task;
    private T result;

    public TaskNodeExecutor(Task<T> task)
    {
        this.task = task;
    }
    
    public void run()
    {
        if(task.isDivisible())
        {
            List<Task<T>> subtasks = task.split();
            List<TaskNodeExecutor<T>> threads = new ArrayList<>();
            for(Task<T> subtask: subtasks)
            {
                TaskNodeExecutor<T> thread = new TaskNodeExecutor<>(subtask);
                threads.add(thread);
                thread.start();
            }
            List<T> subresults = new ArrayList<T>();
            for(TaskNodeExecutor<T> thread: threads)
            {
                try
                {
                    thread.join();
                }
                catch(InterruptedException e)
                {
                }
                subresults.add(thread.getResult());
            }
            result = task.combine(subresults);
        }
        else //!task.isDivisible()
        {
            result = task.execute();
        }
    }

    public T getResult()
    {
        return result;
    }
    
    public static <T> T executeAll(Task<T> task)
    {
        TaskNodeExecutor<T> root = new TaskNodeExecutor<>(task);
        root.run();
        return root.getResult();
    }
}

class BooleanCountingTask implements Task<Integer>
{
    private static final int MIN_LENGTH = 100;
    
    private boolean[] array;
    private int start;
    private int end;
    private int splitFactor;

    public BooleanCountingTask(boolean[] array, int start, int end, int splitFactor)
    {
        this.array = array;
        this.start = start;
        this.end = end;
        this.splitFactor = splitFactor;
    }

    public BooleanCountingTask(boolean[] array, int splitFactor)
    {
        this(array, 0, array.length-1, splitFactor);
    }

    public boolean isDivisible()
    {
        return end-start+1 > MIN_LENGTH;
    }

    public List<Task<Integer>> split()
    {
        int tempStart = start;
        int tempEnd;
        int howMany = (end-start+1) / splitFactor;
        int numberOfTasks = splitFactor;
        if(howMany < 1)
        {
            howMany = 1;
            numberOfTasks = end-start+1;
        }
        List<Task<Integer>> tasks = new ArrayList<>();
        for(int i = 0; i < numberOfTasks; i++)
        {
            if(i < numberOfTasks-1)
            {
                tempEnd = tempStart + howMany - 1;
            }
            else
            {
                tempEnd = end;
            }
            tasks.add(new BooleanCountingTask(array, tempStart, 
                                              tempEnd, splitFactor));
            tempStart = tempEnd + 1;
        }
        return tasks;
    }

    public Integer execute()
    {
        int result = 0;
        for(int i = start; i <= end; i++)
        {
            if(array[i])
            {
                result++;
            }
        }
        return new Integer(result);
    }

    public Integer combine(List<Integer> results)
    {
        int r = 0;
        for(Integer result: results)
        {
            r += result.intValue();
        }
        return new Integer(r);
    }
}

class BooleanCountingResult
{
    private int foundElements;
    private int countedElementsTotal;
    private int countedElementsPerTaskMax;
    private int countedElementsPerTaskMin;
    private int numberOfLeaves;
    private int numberOfNodes;
    private int maxDepth;
    private int minDepth;

    public BooleanCountingResult(int foundElements, int countedElementsTotal,
                                 int countedElementsPerTaskMax,
                                 int countedElementsPerTaskMin,
                                 int numberOfLeaves, int numberOfNodes,
                                 int maxDepth, int minDepth)
    {
        this.foundElements = foundElements;
        this.countedElementsTotal = countedElementsTotal;
        this.countedElementsPerTaskMax = countedElementsPerTaskMax;
        this.countedElementsPerTaskMin = countedElementsPerTaskMin;
        this.numberOfLeaves = numberOfLeaves;
        this.numberOfNodes = numberOfNodes;
        this.maxDepth = maxDepth;
        this.minDepth = minDepth;
    }

    public int getFoundElements()
    {
        return foundElements;
    }

    public int getCountedElementsTotal()
    {
        return countedElementsTotal;
    }

    public int getCountedElementsPerTaskMax()
    {
        return countedElementsPerTaskMax;
    }

    public int getCountedElementsPerTaskMin()
    {
        return countedElementsPerTaskMin;
    }

    public int getNumberOfLeaves()
    {
        return numberOfLeaves;
    }

    public int getNumberOfNodes()
    {
        return numberOfNodes;
    }

    public int getMaxDepth()
    {
        return maxDepth;
    }

    public int getMinDepth()
    {
        return minDepth;
    }
}

class BooleanCountingTaskDeLuxe implements Task<BooleanCountingResult>
{
    private static final int MIN_LENGTH = 10;
    
    private boolean[] array;
    private int start;
    private int end;
    private int splitFactor;

    public BooleanCountingTaskDeLuxe(boolean[] array, int start,
                                     int end, int splitFactor)
    {
        this.array = array;
        this.start = start;
        this.end = end;
        this.splitFactor = splitFactor;
    }

    public BooleanCountingTaskDeLuxe(boolean[] array, int splitFactor)
    {
        this(array, 0, array.length-1, splitFactor);
    }

    public boolean isDivisible()
    {
        return end-start+1 > MIN_LENGTH;
    }

    public List<Task<BooleanCountingResult>> split()
    {
        int tempStart = start;
        int tempEnd;
        int howMany = (end-start+1) / splitFactor;
        int numberOfTasks = splitFactor;
        if(howMany < 1)
        {
            howMany = 1;
            numberOfTasks = end-start+1;
        }
        List<Task<BooleanCountingResult>> tasks = new ArrayList<>();
        for(int i = 0; i < numberOfTasks; i++)
        {
            if(i < numberOfTasks-1)
            {
                tempEnd = tempStart + howMany - 1;
            }
            else
            {
                tempEnd = end;
            }
            tasks.add(new BooleanCountingTaskDeLuxe(array, tempStart, 
                                                    tempEnd, splitFactor));
            tempStart = tempEnd + 1;
        }
        return tasks;
    }

    public BooleanCountingResult execute()
    {
        int result = 0;
        for(int i = start; i <= end; i++)
        {
            if(array[i])
            {
                result++;
            }
        }
        return new BooleanCountingResult(result, end-start+1,
                                         end-start+1, end-start+1,
                                         1, 1, 1, 1);
    }

    public BooleanCountingResult combine(List<BooleanCountingResult> results)
    {
        int foundElements = 0;
        int countedElementsTotal = 0;
        int countedElementsPerTaskMax = 0;
        int countedElementsPerTaskMin = Integer.MAX_VALUE;
        int numberOfLeaves = 0;
        int numberOfNodes = 0;
        int maxDepth = 0;
        int minDepth = Integer.MAX_VALUE;
        for(BooleanCountingResult result: results)
        {
            foundElements += result.getFoundElements();
            countedElementsTotal += result.getCountedElementsTotal();
            if(countedElementsPerTaskMax < result.getCountedElementsPerTaskMax())
            {
                countedElementsPerTaskMax = result.getCountedElementsPerTaskMax();
            }
            if(countedElementsPerTaskMin > result.getCountedElementsPerTaskMin())
            {
                countedElementsPerTaskMin = result.getCountedElementsPerTaskMin();
            }
            numberOfLeaves += result.getNumberOfLeaves();
            numberOfNodes += result.getNumberOfNodes();
            if(maxDepth < result.getMaxDepth())
            {
                maxDepth = result.getMaxDepth();
            }
            if(minDepth > result.getMinDepth())
            {
                minDepth = result.getMinDepth();
            }
        }
        return new BooleanCountingResult(foundElements, countedElementsTotal,
                                         countedElementsPerTaskMax,
                                         countedElementsPerTaskMin,
                                         numberOfLeaves, numberOfNodes+1,
                                         maxDepth+1, minDepth+1);
    }
}

public class TaskTreeExecutor
{
    public static void main(String[] args)
    {
        for(int i = 10; i <= 10000; i++)
        {
            runTest(i);
            if(i % 500 == 0)
            {
                System.out.println("i = " + i);
            }
        }
    }
    
    private static void runTest(int arraySize)
    {
        boolean[] array = new boolean[arraySize];
        for(int i = 0; i < array.length; i++)
        {
            if(i % 2 == 0)
            {
                array[i] = true;
            }
        }

        int splitFactor = 3;
        long startTime = System.currentTimeMillis();
        /*
        BooleanCountingTask task = new BooleanCountingTask(array, splitFactor);
        Integer result = TaskNodeExecutor.executeAll(task);
        long endTime = System.currentTimeMillis();
        long actualTime = endTime - startTime;
        System.out.println("Ergebnis = " + result +
                           " , Rechenzeit  = " +
                           actualTime + " msecs");
        */
        BooleanCountingTaskDeLuxe task = 
                new BooleanCountingTaskDeLuxe(array, splitFactor);
        BooleanCountingResult result = TaskNodeExecutor.executeAll(task);
        long endTime = System.currentTimeMillis();
        long actualTime = endTime - startTime;
        if(result.getMinDepth() != result.getMaxDepth() ||
           result.getFoundElements() != (arraySize%2 == 0 ? arraySize : (arraySize+1))/2)
        {
        System.out.println("gefundene True-Werte: " +
                           result.getFoundElements() +
                           ", gez�hlte Elemente: " +
                           result.getCountedElementsTotal() +
                           ",\nmaximale Anzahl pro Thread: " +
                           result.getCountedElementsPerTaskMax() +
                           ", minimale Anzahl pro Thread: " +
                           result.getCountedElementsPerTaskMin() +
                           ",\nAnzahl der Bl�tter: " +
                           result.getNumberOfLeaves() +
                           ", Anzahl der Knoten: " +
                           result.getNumberOfNodes() +
                           ",\nmaximale Baumtiefe: " +
                           result.getMaxDepth() +
                           ", minimale Baumtiefe: " +
                           result.getMinDepth() +
                           ", Rechenzeit: " +
                           actualTime + " msecs");
        System.out.println("=======================================");
        }
    }
}
