package lab07_pop;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.TextArea;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.awt.Label;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import javax.swing.JTextField;
import javax.swing.JButton;


public class Monitor implements IMonitor{
	
	static ICentrala centralaRMI;

	private JFrame frmMonitor;
	private static TextArea textArea;
	private static TextArea textArea_1;
	private JLabel lblLiczbaOczekujcych;
	private JLabel lblLiczbaOczekujcych_1;
	private JTextField textField;
	private JTextField textField_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Monitor window = new Monitor();
					window.initialize();
					window.RMI(window);
					window.frmMonitor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws RemoteException //DODANE PRZEZ KOMPILATOR
	 */
	public Monitor() throws RemoteException 
	{
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws RemoteException 
	 */
	public void RMI(IMonitor monitorRMI) 
	{
		try 
		{
			Registry registry = LocateRegistry.getRegistry(5033); 
			centralaRMI = (ICentrala) registry.lookup("RMI_Centrala");
			System.out.println("Polaczono z centrala");
			
			IMonitor stub = (IMonitor) UnicastRemoteObject.exportObject(monitorRMI, 0);
			registry.rebind("RMI_Monitor", stub);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	} 
		
	
	@Override
	public void aktualizuj(Info i) throws RemoteException 
	{
		String oczekujace = Integer.toString(i.oczekujace);
		if(i.sprawa.equals("wniosek")) textField.setText(oczekujace);
		if(i.sprawa.equals("odbior")) textField_1.setText(oczekujace);
		
		String dane1 = textArea.getText();
		String dane2 = textArea_1.getText();
		String obslugiwane1 = "";
		String obslugiwane2 = "";
		int rozmiar = i.obslugiwane.size();
		for(int j=0; j<rozmiar; j++) 
		{
			char s = i.obslugiwane.get(j).status;
			if(s=='i' && i.sprawa.equals("wniosek") && i.obslugiwane.get(j).kategoria.equals("wniosek"))	obslugiwane1 = obslugiwane1 + "Nr." + Integer.toString(i.obslugiwane.get(j).numer) + "\t Status: oczekujacy \n";
			if(s=='c'&& i.sprawa.equals("wniosek") && i.obslugiwane.get(j).kategoria.equals("wniosek"))	obslugiwane1 = obslugiwane1 + "Nr." + Integer.toString(i.obslugiwane.get(j).numer) + "\t Status: wezwany \n";
			if(s=='i' && i.sprawa.equals("odbior") && i.obslugiwane.get(j).kategoria.equals("odbior"))	obslugiwane2 = obslugiwane2 + "Nr." + Integer.toString(i.obslugiwane.get(j).numer) + "\t Status: oczekujacy \n";
			if(s=='c'&& i.sprawa.equals("odbior") && i.obslugiwane.get(j).kategoria.equals("odbior"))	obslugiwane2 = obslugiwane2 + "Nr." + Integer.toString(i.obslugiwane.get(j).numer) + "\t Status: wezwany \n";
		}
		if(i.sprawa.equals("wniosek"))	{textArea.setText(obslugiwane1); textArea_1.setText(dane2);}
		if(i.sprawa.equals("odbior"))	{textArea.setText(dane1); textArea_1.setText(obslugiwane2);}
	}
	
	
	private void initialize() throws RemoteException {
		frmMonitor = new JFrame();
		frmMonitor.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frmMonitor.setTitle("Monitor");
		frmMonitor.setBounds(100, 100, 588, 465);
		frmMonitor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMonitor.getContentPane().setLayout(null);
		
		this.textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 176, 273, 240);
		frmMonitor.getContentPane().add(textArea);
		
		JLabel lblWyswietlacz = new JLabel("Wyswietlacz");
		lblWyswietlacz.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblWyswietlacz.setBounds(228, 11, 119, 25);
		frmMonitor.getContentPane().add(lblWyswietlacz);
		
		this.textArea_1 = new TextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(289, 176, 273, 240);
		frmMonitor.getContentPane().add(textArea_1);
		
		JLabel lblWniosekOPrawo = new JLabel("Wniosek o prawo jazdy");
		lblWniosekOPrawo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWniosekOPrawo.setBounds(75, 56, 166, 25);
		frmMonitor.getContentPane().add(lblWniosekOPrawo);
		
		JLabel lblOdbiorPrawaJazdy = new JLabel("Odbior prawa jazdy");
		lblOdbiorPrawaJazdy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOdbiorPrawaJazdy.setBounds(374, 56, 124, 25);
		frmMonitor.getContentPane().add(lblOdbiorPrawaJazdy);
		
		lblLiczbaOczekujcych = new JLabel("Liczba oczekuj\u0105cych:");
		lblLiczbaOczekujcych.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLiczbaOczekujcych.setBounds(10, 106, 132, 14);
		frmMonitor.getContentPane().add(lblLiczbaOczekujcych);
		
		lblLiczbaOczekujcych_1 = new JLabel("Liczba oczekuj\u0105cych:");
		lblLiczbaOczekujcych_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLiczbaOczekujcych_1.setBounds(301, 106, 124, 14);
		frmMonitor.getContentPane().add(lblLiczbaOczekujcych_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setBounds(170, 104, 86, 20);
		frmMonitor.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_1.setBounds(461, 104, 86, 20);
		frmMonitor.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblListaAktualnieObslugiwanych = new JLabel("Lista obslugiwanych");
		lblListaAktualnieObslugiwanych.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblListaAktualnieObslugiwanych.setBounds(75, 156, 166, 14);
		frmMonitor.getContentPane().add(lblListaAktualnieObslugiwanych);
		
		JLabel lblListaAktualnieObslugiwanych_1 = new JLabel("Lista obslugiwanych");
		lblListaAktualnieObslugiwanych_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblListaAktualnieObslugiwanych_1.setBounds(361, 156, 166, 14);
		frmMonitor.getContentPane().add(lblListaAktualnieObslugiwanych_1);
		
	}
}
