package lab07_pop;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMonitor extends Remote 
{
	public void aktualizuj(Info i1) throws RemoteException;
}
