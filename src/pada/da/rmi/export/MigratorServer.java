package pada.da.rmi.export;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MigratorServer {
    public static void main(String args[]) {
        try {
            LocateRegistry.createRegistry(1099);
            Naming.rebind("Migrator", new MigratorImpl());
            System.out.println("Migrations-Server hochgefahren.");
        } catch (Exception e) {
            System.out.println("Ausnahme: " + e.getMessage());
        }
    }
}