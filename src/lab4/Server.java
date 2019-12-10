package lab4;

import db.DbConfigs;
import db.DbShop;
import db.IDbServices;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    private static final int PORT = 12300;

    public static void main(String args[]) throws Exception {
        DbShop services = new DbShop();
        services.connect(DbConfigs.URL, DbConfigs.USER, DbConfigs.PASSWORD);

        IDbServices stub = (IDbServices) UnicastRemoteObject.exportObject(services, 0);

        Registry registry = LocateRegistry.createRegistry(PORT);

        registry.bind("IServices", stub);

        System.err.println("Server ready");
    }
}