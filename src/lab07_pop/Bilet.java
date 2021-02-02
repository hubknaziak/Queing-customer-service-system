package lab07_pop;

import java.io.Serializable;
import java.rmi.RemoteException;

@SuppressWarnings("serial")
public class Bilet implements Serializable
{
	public String kategoria;
	public int numer;
	public char status; //'i' - issued, 'c' - called, 's'-served
	
	Bilet()
	{
		
	}
	
	Bilet( String kategoria, int numer, char status)
	{
		this.kategoria=kategoria;
		this.numer=numer;
		this.status=status;
	}
	
	
//	@Override
//	public boolean zarejestruj(IMonitor m) throws RemoteException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	//public Bilet wydajBilet(Bilet ticketServed) throws RemoteException
//	public Bilet wydajBilet(String kategoria, int numer, char status) throws RemoteException 
//	{
//		Bilet bilet = new Bilet(kategoria, numer, status);
//		return bilet;
//	}
//
//	@Override
//	public void aktualizuj() throws RemoteException
//	{
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String wymianaWiadomosci1(String wiadomosc) throws RemoteException 
//	{
//			if (wiadomosc != null) 
//			{
//			Monitor m1 = new Monitor();
//			m1.komunikacja1(wiadomosc);
////			System.out.println("Serwer odbiera: " + wiadomosc);
////			wiadomosc = wiadomosci[ktory1++];
////			System.out.println("Serwer wysy³a: " + wiadomosc);
////			if (ktory1 == 4)
////			ktory1 = 0;
//			return wiadomosc;
//			}
//			return null;
//	}
//	//@Override
////	public String wymianaWiadomosci1(String wiadomosc) {
////				if (wiadomosc != null) {
////				System.out.println("Serwer odbiera: " + wiadomosc);
////				wiadomosc = wiadomosci[ktory1++];
////				System.out.println("Serwer wysy³a: " + wiadomosc);
////				if (ktory1 == 4)
////				ktory1 = 0;
////				return wiadomosc;
////				}
////				return null;
////	}
}
