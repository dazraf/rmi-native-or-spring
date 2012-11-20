package fuzz.computeserver;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import fuzz.computeinterface.Compute;
import fuzz.computeserver.RegistrationStrategyFactory.RegistrationStrategy;
import fuzz.computeserver.RegistrationStrategyFactory.StrategyType;

public class ServerApp {
    private RegistrationStrategy rs;
    
    public ServerApp(final StrategyType st) throws RemoteException {
        this.rs = RegistrationStrategyFactory.getStrategy(st, Compute.REGISTRY_PORT);
    }

    public static void main(final String[] args) throws IOException, NotBoundException {
        if (args.length == 0) {
            System.out.println("spring|native");
            System.exit(0);
        }
        StrategyType st = StrategyType.valueOf(args[0].trim().toUpperCase());
        ServerApp sa = new ServerApp(st);
        sa.run();
    }

    private void run() throws IOException, NotBoundException {
        startup();
        System.out.println("Press <Enter> to shutdown");
        System.in.read();
        System.out.println("Shutting down");
        shutdown();
    }

    public void shutdown() throws AccessException, RemoteException, NotBoundException {
        rs.unregister(Compute.SERVICE_NAME);
    }

    public void startup() throws RemoteException {
        rs.register(Compute.SERVICE_NAME, new ComputeEngine(), Compute.class);
    }
 }
