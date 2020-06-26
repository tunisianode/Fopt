package pada.pa.advanced;

class AssignedState {
    private int resourcesTotal;
    private int[] resourcesAssigned;
    private int[] resourcesStillNeeded;
    private int resourcesAvailable;

    public AssignedState(int resourcesTotal, int[] resourcesMaximum) {
        this.resourcesTotal = resourcesTotal;
        this.resourcesAssigned = new int[resourcesMaximum.length];
        this.resourcesStillNeeded = resourcesMaximum.clone();
        this.resourcesAvailable = resourcesTotal;
    }

    private AssignedState() {
    }

    private AssignedState makeCopy() {
        AssignedState b = new AssignedState();
        b.resourcesTotal = resourcesTotal;
        b.resourcesAssigned = resourcesAssigned.clone();
        b.resourcesStillNeeded = resourcesStillNeeded.clone();
        b.resourcesAvailable = resourcesAvailable;
        return b;
    }

    public int getAvailableResources() {
        return resourcesAvailable;
    }

    public void assign(int thread, int number) {
        resourcesAssigned[thread] += number;
        resourcesStillNeeded[thread] -= number;
        resourcesAvailable -= number;
    }

    public void free(int thread, int number) {
        resourcesAssigned[thread] -= number;
        resourcesStillNeeded[thread] += number;
        resourcesAvailable += number;
    }

    private boolean hasThreadTerminated(int thread) {
        return (resourcesAssigned[thread] == 0 && resourcesStillNeeded[thread] == 0);
    }

    private boolean haveAllThreadsTerminated() {
        for (int i = 0; i < resourcesStillNeeded.length; i++)
            if (!hasThreadTerminated(i))
                return false;
        return true;
    }

    public boolean isSafe(int thread, int number) {
        AssignedState t = makeCopy();
        t.assign(thread, number);
        System.out.print(" Test: ");
        while (!t.haveAllThreadsTerminated()) {
            /*
             * suche einen, der noch nicht beendet ist und der zu Ende
             * laufen kann
             */
            int candidate = -1;
            for (int i = 0; i < t.resourcesStillNeeded.length; i++) {
                if (!t.hasThreadTerminated(i)
                        && t.resourcesStillNeeded[i] <= t.resourcesAvailable) {
                    candidate = i;
                    break;
                }
            }
            if (candidate == -1) {
                // kein Thread gefunden, der zu Ende laufen kann
                return false; // Zustand nicht sicher
            }
            // Thread kann zu Ende laufen, gibt alle Betriebsmittel
            // frei
            t.resourcesAvailable += t.resourcesAssigned[candidate];
            t.resourcesAssigned[candidate] = 0;
            t.resourcesStillNeeded[candidate] = 0;
        }
        return true;
    }
}

public class ResourceManagerBanker {
    private AssignedState state;

    public ResourceManagerBanker(int resourcesTotal, int[] resourcesMaximum) {
        state = new AssignedState(resourcesTotal, resourcesMaximum);
    }

    public synchronized boolean acquire(int thread, int number) {
        if (state.getAvailableResources() < number
                || !state.isSafe(thread, number)) {
            return false;
        }
        state.assign(thread, number);
        return true;
    }

    public synchronized void release(int thread, int number) {
        state.free(thread, number);
    }
}