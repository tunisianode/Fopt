package pada.pa.advanced;

public class ResourceManagerNaive implements ResourceManager
{
    private SemaphoreGroup availableResources;

    public ResourceManagerNaive(int[] initialResources)
    {
        availableResources = new SemaphoreGroup(initialResources.length);
        availableResources.changeValues(initialResources);
    }

    public void acquire(int[] resources)
    {
        /*
         * angeforderte Ressourcen müssen von verfügbaren Ressourcen
         * abgezogen werden
         */
        int[] tmp = new int[resources.length];
        for(int i = 0; i < resources.length; i++)
            tmp[i] = -resources[i];
        availableResources.changeValues(tmp);
    }

    public void release(int[] resources)
    {
        /*
         * zurückgegebene Ressourcen müssen zu verfügbaren Ressourcen
         * addiert werden
         */
        availableResources.changeValues(resources);
    }
}