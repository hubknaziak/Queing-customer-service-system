package lab07_pop;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICentrala extends Remote 
{
	//public String wymianaWiadomosci1(String wiadomosc) throws RemoteException;//DO TESTOW
	public boolean zarejestruj(IMonitor m) throws RemoteException;
	//public Bilet wydajBilet (Bilet ticketServed) throws RemoteException;
	public Bilet wydajBilet (String kategoria, char status) throws RemoteException;
	//public void aktualizuj() throws RemoteException;
}
