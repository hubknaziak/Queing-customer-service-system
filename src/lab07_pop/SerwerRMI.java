//package lab07_pop;
//
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.rmi.server.ExportException;
//import java.rmi.server.UnicastRemoteObject;
//
//import javax.swing.JOptionPane;
//
//public class SerwerRMI {
//
////	public static void main(String[] args) 
////	{
////		try
////		{
////			ICentrala obiekt = new Centrala(); // 2
////			//Naming.rebind("rmi://localhost:1099/CalculatorService", obiekt);
////			ICentrala stub = (ICentrala) UnicastRemoteObject.exportObject(obiekt, 0); // 3
////			Registry registry = LocateRegistry.createRegistry(5000); // 4
////			registry.rebind("RMI_Kolejka", stub); // 5
////			JOptionPane.showMessageDialog(null, "Serwer gotowy do obslugi aplikacji RMI");
////		}
////		catch(ExportException e)
////		{
////			JOptionPane.showMessageDialog(null, "Serwer gotowy do obslugi aplikacji RMI, port na którym pracuje program jest juz do niego podlaczony");
////		}
////		catch (/*NotBoundException | */Exception e) 
////		{
////			JOptionPane.showMessageDialog(null, "Blad przy uruchomieniu serwera");
////		}
////	}
//
//}
