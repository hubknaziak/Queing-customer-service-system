package lab07_pop;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Centrala implements ICentrala
{
	static int monitory = 0;
	static int numerBiletuw = 0;
	static int numerBiletuo = 0;
	
	@Override
	public boolean zarejestruj(IMonitor monitorRMI) throws RemoteException //Nieuzywane, problem z marshalem
	{
		try
		{
		IMonitor stub = (IMonitor) UnicastRemoteObject.exportObject(monitorRMI, 0);
		Registry registry = LocateRegistry.getRegistry(5021);	
		registry.rebind("RMI_Monitor_" + monitory, stub);
		monitory++;
		}
		catch(ExportException e)
		{
			JOptionPane.showMessageDialog(null, "Blad exportu obiektu");
			return false;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Blad rejestracji monitora");
			System.out.println("Wyjatek serwera 2: " + e);
			return false;
		}
		return true;
	}

	@Override
	public Bilet wydajBilet(String kategoria, char status) throws RemoteException 
	{
		if(kategoria.equals("wniosek"))
		{
			numerBiletuw++;
			Bilet bilet = new Bilet(kategoria, numerBiletuw, status);
			return bilet;
		}
		else
		{
			numerBiletuo++;
			Bilet bilet = new Bilet(kategoria, numerBiletuo, status);
			return bilet;
		}
	
	}

	
	public static void main(String[] args) 
	{
		try
		{
			try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Info.bin"))) 
			{
				List<Bilet> bilet = new ArrayList<Bilet>();
				Info info1 = new Info(bilet, 0, "wniosek");
				Info info2 = new Info(bilet, 0, "odbior");
				output.writeObject(info1);
			    output.writeObject(info2);
			    output.close();
			}
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "Blad przy probie serializacji");
			}
			
			ICentrala obiekt = new Centrala(); 
			ICentrala stub = (ICentrala) UnicastRemoteObject.exportObject(obiekt, 0);
			Registry registry = LocateRegistry.createRegistry(5033); 
			registry.rebind("RMI_Centrala", stub); 
			JOptionPane.showMessageDialog(null, "Serwer gotowy do obslugi aplikacji RMI");
		}		 
		catch(ExportException e)
		{
			JOptionPane.showMessageDialog(null, "Serwer gotowy do obslugi aplikacji RMI, port na którym pracuje program jest juz do niego podlaczony");
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Blad przy uruchomieniu serwera");
			System.out.println(e);
		}
		
	}

}
