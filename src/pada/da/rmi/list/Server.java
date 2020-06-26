package pada.da.rmi.list;

import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            AppendImpl server = new AppendImpl();
            Naming.rebind("AppendServer", server);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}